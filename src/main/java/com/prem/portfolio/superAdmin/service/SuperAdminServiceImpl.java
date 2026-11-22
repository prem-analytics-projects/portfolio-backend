
package com.prem.portfolio.superadmin.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.auth.entity.Role;
import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.entity.UserRole;
import com.prem.portfolio.auth.enums.AccountStatus;
import com.prem.portfolio.auth.enums.ApprovalStatus;
import com.prem.portfolio.auth.repository.RoleRepository;
import com.prem.portfolio.auth.repository.UserRepository;
import com.prem.portfolio.auth.repository.UserRoleRepository;
import com.prem.portfolio.auth.service.RefreshTokenService;
import com.prem.portfolio.common.exception.ResourceNotFoundException;
import com.prem.portfolio.superadmin.dto.SuperAdminUserResponse;
import com.prem.portfolio.superadmin.mapper.SuperAdminMapper;
import com.prem.portfolio.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SuperAdminServiceImpl implements SuperAdminService {

    private static final String SUPER_ADMIN = "SUPER_ADMIN";
    private static final String USER_ROLE = "USER";

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final SuperAdminMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<SuperAdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> mapper.toResponse(
                        user,
                        userRoleRepository.findByUser_UserId(
                                user.getUserId()
                        )
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SuperAdminUserResponse getUserById(Long userId) {

        User user = findUser(userId);

        return toResponse(user);
    }

    @Override
    public SuperAdminUserResponse approveRegistration(Long userId) {

        User user = findUser(userId);
        User actor = getCurrentActor();

        List<UserRole> pendingRoles =
                userRoleRepository
                        .findByUser_UserIdAndApprovalStatus(
                                userId,
                                ApprovalStatus.PENDING
                        );

        UserRole pendingUserRole = pendingRoles.stream()
                .filter(ur -> USER_ROLE.equalsIgnoreCase(
                        ur.getRole().getRoleName()
                ))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No pending USER registration was found."
                        )
                );

        OffsetDateTime now = OffsetDateTime.now();

        pendingUserRole.setApprovalStatus(ApprovalStatus.APPROVED);
        pendingUserRole.setApprovedBy(actor);
        pendingUserRole.setApprovedAt(now);
        pendingUserRole.setRejectionReason(null);
        pendingUserRole.setUpdatedBy(actor);

        userRoleRepository.save(pendingUserRole);

        return toResponse(user);
    }

    @Override
    public SuperAdminUserResponse rejectRegistration(
            Long userId,
            String reason
    ) {
        User user = findUser(userId);
        User actor = getCurrentActor();

        List<UserRole> pendingRoles =
                userRoleRepository
                        .findByUser_UserIdAndApprovalStatus(
                                userId,
                                ApprovalStatus.PENDING
                        );

        UserRole pendingUserRole = pendingRoles.stream()
                .filter(ur -> USER_ROLE.equalsIgnoreCase(
                        ur.getRole().getRoleName()
                ))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No pending USER registration was found."
                        )
                );

        String rejectionReason =
                (reason == null || reason.isBlank())
                        ? "Registration rejected by Super Admin."
                        : reason.trim();

        pendingUserRole.setApprovalStatus(ApprovalStatus.REJECTED);
        pendingUserRole.setRejectionReason(rejectionReason);
        pendingUserRole.setApprovedBy(actor);
        pendingUserRole.setApprovedAt(OffsetDateTime.now());
        pendingUserRole.setUpdatedBy(actor);

        userRoleRepository.save(pendingUserRole);

        // Revoke any existing sessions for this user.
        refreshTokenService.revokeAllUserTokens(userId);

        return toResponse(user);
    }

    @Override
    public SuperAdminUserResponse updateUserRole(
            Long userId,
            String roleName
    ) {
        User targetUser = findUser(userId);
        User actor = getCurrentActor();

        String normalizedRoleName =
                roleName.trim().toUpperCase(Locale.ROOT);

        Role newRole = roleRepository
                .findByRoleNameIgnoreCase(normalizedRoleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role does not exist: " + normalizedRoleName
                        )
                );

        List<UserRole> existingAssignments =
                userRoleRepository.findByUser_UserId(userId);

        boolean currentlySuperAdmin = existingAssignments.stream()
                .anyMatch(ur ->
                        ur.getApprovalStatus() == ApprovalStatus.APPROVED
                        && SUPER_ADMIN.equalsIgnoreCase(
                                ur.getRole().getRoleName()
                        )
                );

        boolean changingAwayFromSuperAdmin =
                currentlySuperAdmin
                && !SUPER_ADMIN.equals(normalizedRoleName);

        if (changingAwayFromSuperAdmin) {
            ensureAnotherActiveSuperAdminExists(userId);
        }

        // Replace the user's existing role assignments.
        // The schema has no REVOKED status, so replaced roles
        // are marked REJECTED with an explanatory reason.
        for (UserRole assignment : existingAssignments) {

            if (assignment.getApprovalStatus()
                    == ApprovalStatus.APPROVED) {

                assignment.setApprovalStatus(ApprovalStatus.REJECTED);
                assignment.setRejectionReason(
                        "Role replaced by Super Admin."
                );
                assignment.setUpdatedBy(actor);
            }
        }

        UserRole targetAssignment =
                existingAssignments.stream()
                        .filter(ur ->
                                ur.getRole().getRoleId()
                                        .equals(newRole.getRoleId())
                        )
                        .findFirst()
                        .orElseGet(() ->
                                new UserRole(targetUser, newRole)
                        );

        targetAssignment.setApprovalStatus(ApprovalStatus.APPROVED);
        targetAssignment.setApprovedBy(actor);
        targetAssignment.setApprovedAt(OffsetDateTime.now());
        targetAssignment.setRejectionReason(null);
        targetAssignment.setUpdatedBy(actor);

        userRoleRepository.saveAll(existingAssignments);
        userRoleRepository.save(targetAssignment);

        // Invalidate old refresh tokens after a privilege change.
        refreshTokenService.revokeAllUserTokens(userId);

        return toResponse(targetUser);
    }

    @Override
    public SuperAdminUserResponse updateUserStatus(
            Long userId,
            AccountStatus accountStatus
    ) {
        User targetUser = findUser(userId);
        User actor = getCurrentActor();

        if (accountStatus == null) {
            throw new IllegalArgumentException(
                    "Account status is required."
            );
        }

        boolean isActiveSuperAdmin =
                isApprovedSuperAdmin(targetUser);

        boolean disablingAccount =
                accountStatus != AccountStatus.ACTIVE;

        if (isActiveSuperAdmin && disablingAccount) {
            ensureAnotherActiveSuperAdminExists(userId);
        }

        targetUser.setAccountStatus(accountStatus);
        targetUser.setUpdatedBy(actor.getUserId());

        userRepository.save(targetUser);

        if (disablingAccount) {
            refreshTokenService.revokeAllUserTokens(userId);
        }

        return toResponse(targetUser);
    }

    
private User findUser(Long userId) {

    if (userId == null || userId <= 0) {
        throw new IllegalArgumentException(
                "A valid user ID is required."
        );
    }

    return userRepository.findById(userId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found: " + userId
                    )
            );
}


    private User getCurrentActor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName() == null) {

            throw new IllegalStateException(
                    "Authenticated Super Admin was not found."
            );
        }

        return userRepository
                .findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Current Super Admin account was not found."
                        )
                );
    }

    private SuperAdminUserResponse toResponse(User user) {

        return mapper.toResponse(
                user,
                userRoleRepository.findByUser_UserId(user.getUserId())
        );
    }

    private boolean isApprovedSuperAdmin(User user) {

        return userRoleRepository.findByUser_UserId(user.getUserId())
                .stream()
                .anyMatch(ur ->
                        ur.getApprovalStatus() == ApprovalStatus.APPROVED
                        && SUPER_ADMIN.equalsIgnoreCase(
                                ur.getRole().getRoleName()
                        )
                );
    }

    private void ensureAnotherActiveSuperAdminExists(
            Long excludedUserId
    ) {
        long otherActiveSuperAdmins =
                userRepository.findAll().stream()
                        .filter(user ->
                                !user.getUserId().equals(excludedUserId)
                        )
                        .filter(user ->
                                user.getAccountStatus()
                                        == AccountStatus.ACTIVE
                        )
                        .filter(this::isApprovedSuperAdmin)
                        .count();

        if (otherActiveSuperAdmins == 0) {
            throw new IllegalStateException(
                    "You cannot remove or disable the last active "
                    + "Super Admin account."
            );
        }
    }
}


package com.prem.portfolio.superadmin.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.entity.UserRole;
import com.prem.portfolio.superadmin.dto.SuperAdminUserResponse;

@Component
public class SuperAdminMapper {

    public SuperAdminUserResponse toResponse(
            User user,
            List<UserRole> userRoles
    ) {
        List<SuperAdminUserResponse.RoleAssignment> roles =
                userRoles.stream()
                        .map(this::toRoleAssignment)
                        .toList();

        return new SuperAdminUserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobile(),
                user.getEmailVerifiedAt(),
                user.getAccountStatus().name(),
                user.getCreatedAt(),
                roles
        );
    }

    private SuperAdminUserResponse.RoleAssignment toRoleAssignment(
            UserRole userRole
    ) {
        return new SuperAdminUserResponse.RoleAssignment(
                userRole.getRole().getRoleName(),
                userRole.getApprovalStatus().name(),
                userRole.getRequestedAt(),
                userRole.getApprovedAt(),
                userRole.getRejectionReason()
        );
    }
}

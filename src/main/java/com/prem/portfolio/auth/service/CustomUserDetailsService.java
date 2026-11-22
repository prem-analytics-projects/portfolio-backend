
package com.prem.portfolio.auth.service;

import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.entity.UserRole;
import com.prem.portfolio.auth.repository.UserRepository;
import com.prem.portfolio.auth.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email: " + email
                ));

        List<UserRole> userRoles =
                userRoleRepository.findByUser_UserId(user.getUserId());

        // Only approved roles become Spring Security authorities.
        Collection<GrantedAuthority> authorities = userRoles.stream()
                .filter(userRole ->
                        userRole.getApprovalStatus() != null
                        && userRole.getApprovalStatus().name()
                                .equals("APPROVED")
                )
                .map(userRole -> {
                    String roleName = userRole.getRole()
                            .getRoleName()
                            .trim()
                            .toUpperCase(Locale.ROOT);

                    if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                    }

                    return new SimpleGrantedAuthority(roleName);
                })
                .distinct()
                .map(authority -> (GrantedAuthority) authority)
                .toList();

        boolean emailVerified = user.getEmailVerifiedAt() != null;

        boolean accountActive =
                user.getAccountStatus() != null
                && user.getAccountStatus().name().equals("ACTIVE");

        boolean accountLocked =
                user.getAccountStatus() != null
                && user.getAccountStatus().name().equals("LOCKED");

        // A user must have at least one approved role.
        boolean hasApprovedRole = !authorities.isEmpty();

        // Login requires all three conditions:
        // 1. Email verified
        // 2. Account ACTIVE
        // 3. At least one approved role
        boolean accountEnabled =
                emailVerified && accountActive && hasApprovedRole;

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(accountLocked)
                .credentialsExpired(false)
                .disabled(!accountEnabled)
                .build();
    }
}

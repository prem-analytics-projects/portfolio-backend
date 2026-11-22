
package com.prem.portfolio.auth.service;

import com.prem.portfolio.auth.dto.request.RegisterRequest;
import com.prem.portfolio.auth.dto.response.AuthenticationResponse;
import com.prem.portfolio.auth.dto.response.UserResponse;

import com.prem.portfolio.auth.entity.RefreshToken;
import com.prem.portfolio.auth.entity.Role;
import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.entity.UserRole;

import com.prem.portfolio.auth.enums.ApprovalStatus;
import com.prem.portfolio.auth.enums.AccountStatus;

import com.prem.portfolio.auth.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;

    private final EmailVerificationService emailVerificationService;
    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    // -----------------------------------------
    // 1. USER REGISTRATION
    // -----------------------------------------

    public UserResponse register(RegisterRequest request) {

        String email = request.email().trim().toLowerCase(Locale.ROOT);

        if (userService.emailExists(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists."
            );
        }

        User user = new User(
        request.firstName().trim(),
        request.lastName().trim(),
        email,
        request.mobile(),
        passwordEncoder.encode(request.password())
);

        user.setAccountStatus(AccountStatus.ACTIVE);

        User savedUser = userService.saveUser(user);

        // Assign default USER role.
        Role defaultRole = roleService.getRequiredRole("USER");

        UserRole userRole = new UserRole(savedUser, defaultRole);

        userRole.setApprovalStatus(ApprovalStatus.PENDING);

        userRoleRepository.save(userRole);

        // Send actual SMTP verification email.
        emailVerificationService.createAndSendVerificationToken(
                savedUser
        );

        return mapUserResponse(savedUser);
    }

    // -----------------------------------------
    // 2. USER LOGIN
    // -----------------------------------------

    public AuthenticationResponse login(
            String email,
            String password) {

        String normalizedEmail =
                email.trim().toLowerCase(Locale.ROOT);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                normalizedEmail,
                                password
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        User user = userService.getUserByEmail(normalizedEmail);

        String accessToken =
                jwtService.generateAccessToken(userDetails);

        String refreshToken =
                refreshTokenService.createRefreshToken(user);

        UserResponse userResponse = mapUserResponse(user);

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpirationMs() / 1000,
                userResponse
        );
    }

    // -----------------------------------------
    // 3. EMAIL VERIFICATION
    // -----------------------------------------

    public void verifyEmail(String token) {

        emailVerificationService.verifyEmail(token);
    }

    // -----------------------------------------
    // 4. RESEND VERIFICATION EMAIL
    // -----------------------------------------

    public void resendVerificationEmail(String email) {

        User user = userService.getUserByEmail(
                email.trim().toLowerCase(Locale.ROOT)
        );

        if (user.getEmailVerifiedAt() != null) {
            throw new IllegalStateException(
                    "Email address is already verified."
            );
        }

        emailVerificationService.createAndSendVerificationToken(
                user
        );
    }

    // -----------------------------------------
    // 5. REFRESH ACCESS TOKEN
    // -----------------------------------------

    public AuthenticationResponse refreshAccessToken(
            String rawRefreshToken) {

        RefreshToken storedToken =
                refreshTokenService.validateRefreshToken(
                        rawRefreshToken
                );

        User user = storedToken.getUser();

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        user.getEmail()
                );
                if (!userDetails.isEnabled()
        || !userDetails.isAccountNonLocked()) {

    throw new IllegalStateException(
            "Your account is not eligible to refresh tokens."
    );
}
        // Rotate the existing refresh token.
        String newRefreshToken =
                refreshTokenService.rotateRefreshToken(
                        rawRefreshToken
                );

        String newAccessToken =
                jwtService.generateAccessToken(userDetails);

        return new AuthenticationResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                jwtService.getAccessTokenExpirationMs() / 1000,
                mapUserResponse(user)
        );
    }

    // -----------------------------------------
    // 6. LOGOUT
    // -----------------------------------------

    public void logout(String rawRefreshToken) {

        refreshTokenService.revokeRefreshToken(
                rawRefreshToken
        );
    }

    // -----------------------------------------
    // 7. LOGOUT FROM ALL DEVICES
    // -----------------------------------------

    public void logoutAll() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userService.getUserByEmail(email);

        refreshTokenService.revokeAllUserTokens(
                user.getUserId()
        );
    }

    // -----------------------------------------
    // 8. GET CURRENT USER
    // -----------------------------------------

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userService.getUserByEmail(email);

        return mapUserResponse(user);
    }

    // -----------------------------------------
    // 9. USER RESPONSE MAPPER
    // -----------------------------------------

    private UserResponse mapUserResponse(User user) {

        List<String> roles =
                userRoleRepository.findByUser_UserId(
                        user.getUserId()
                )
                .stream()
                .filter(userRole ->
                        userRole.getApprovalStatus()
                                == ApprovalStatus.APPROVED
                )
                .map(userRole ->
                        userRole.getRole().getRoleName()
                )
                .distinct()
                .toList();

        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobile(),
                user.getEmailVerifiedAt(),
                user.getAccountStatus(),
                roles
        );
    }
}

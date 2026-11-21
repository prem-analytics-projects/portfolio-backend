
package com.prem.portfolio.auth.controller;

import com.prem.portfolio.auth.dto.request.RegisterRequest;
import com.prem.portfolio.auth.dto.request.LoginRequest;
import com.prem.portfolio.auth.dto.request.VerifyEmailRequest;
import com.prem.portfolio.auth.dto.request.ResendVerificationRequest;
import com.prem.portfolio.auth.dto.request.RefreshTokenRequest;
import com.prem.portfolio.auth.dto.request.LogoutRequest;

import com.prem.portfolio.auth.dto.response.AuthenticationResponse;
import com.prem.portfolio.auth.dto.response.UserResponse;

import com.prem.portfolio.auth.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // -----------------------------------------
    // 1. REGISTER
    // -----------------------------------------

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // -----------------------------------------
    // 2. LOGIN
    // -----------------------------------------

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthenticationResponse response =
                authService.login(
                        request.email(),
                        request.password()
                );

        return ResponseEntity.ok(response);
    }

    // -----------------------------------------
    // 3. VERIFY EMAIL
    // -----------------------------------------

    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request) {

        authService.verifyEmail(request.token());

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Email verified successfully."
                )
        );
    }

    // -----------------------------------------
    // 4. RESEND VERIFICATION EMAIL
    // -----------------------------------------

    @PostMapping("/resend-verification")
    public ResponseEntity<Map<String, String>> resendVerification(
            @Valid @RequestBody ResendVerificationRequest request) {

        authService.resendVerificationEmail(request.email());

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Verification email sent successfully."
                )
        );
    }

    // -----------------------------------------
    // 5. REFRESH ACCESS TOKEN
    // -----------------------------------------

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        AuthenticationResponse response =
                authService.refreshAccessToken(
                        request.refreshToken()
                );

        return ResponseEntity.ok(response);
    }

    // -----------------------------------------
    // 6. LOGOUT
    // -----------------------------------------

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @Valid @RequestBody LogoutRequest request) {

        authService.logout(request.refreshToken());

        return ResponseEntity.ok(
                Map.of("message", "Logged out successfully.")
        );
    }

    // -----------------------------------------
    // 7. LOGOUT ALL DEVICES
    // -----------------------------------------

    @PostMapping("/logout-all")
    public ResponseEntity<Map<String, String>> logoutAll() {

        authService.logoutAll();

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Logged out from all devices successfully."
                )
        );
    }

    // -----------------------------------------
    // 8. CURRENT USER
    // -----------------------------------------

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        UserResponse response = authService.getCurrentUser();

        return ResponseEntity.ok(response);
    }
}

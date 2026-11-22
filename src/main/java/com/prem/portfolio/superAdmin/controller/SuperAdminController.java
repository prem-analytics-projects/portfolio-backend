
package com.prem.portfolio.superadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.auth.enums.AccountStatus;
import com.prem.portfolio.superadmin.dto.SuperAdminUserResponse;
import com.prem.portfolio.superadmin.dto.UpdateUserRoleRequest;
import com.prem.portfolio.superadmin.dto.UpdateUserStatusRequest;
import com.prem.portfolio.superadmin.service.SuperAdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/super-admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    // Dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> dashboard() {

        return ResponseEntity.ok(
                Map.of(
                        "message", "Welcome to Super Admin Dashboard",
                        "role", "SUPER_ADMIN",
                        "status", "ACTIVE"
                )
        );
    }

    // Get all registered users
    @GetMapping("/users")
    public ResponseEntity<List<SuperAdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                superAdminService.getAllUsers()
        );
    }

    // Get one user
    @GetMapping("/users/{userId}")
    public ResponseEntity<SuperAdminUserResponse> getUserById(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                superAdminService.getUserById(userId)
        );
    }

    // Approve registration
    @PatchMapping("/users/{userId}/approve")
    public ResponseEntity<SuperAdminUserResponse> approveRegistration(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                superAdminService.approveRegistration(userId)
        );
    }

    // Reject registration
    @PatchMapping("/users/{userId}/reject")
    public ResponseEntity<SuperAdminUserResponse> rejectRegistration(
            @PathVariable Long userId,
            @RequestParam(required = false) String reason
    ) {
        return ResponseEntity.ok(
                superAdminService.rejectRegistration(userId, reason)
        );
    }

    // Replace user's role
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<SuperAdminUserResponse> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRoleRequest request
    ) {
        return ResponseEntity.ok(
                superAdminService.updateUserRole(
                        userId,
                        request.roleName()
                )
        );
    }

    // Activate, disable or lock account
    @PatchMapping("/users/{userId}/status")
    public ResponseEntity<SuperAdminUserResponse> updateUserStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserStatusRequest request
    ) {
        AccountStatus status = request.accountStatus();

        return ResponseEntity.ok(
                superAdminService.updateUserStatus(
                        userId,
                        status
                )
        );
    }
}

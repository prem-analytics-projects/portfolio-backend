
package com.prem.portfolio.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prem.portfolio.admin.service.AdminDashboardService;
import com.prem.portfolio.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard(
            Authentication authentication
    ) {

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Map<String, Object> dashboardData = Map.of(
                "message", "Welcome to Admin Dashboard",
                "authenticatedUser", authentication.getName(),
                "roles", roles,
                "statistics", adminDashboardService.getDashboardStatistics()
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Admin dashboard loaded successfully",
                        dashboardData
                )
        );
    }
}

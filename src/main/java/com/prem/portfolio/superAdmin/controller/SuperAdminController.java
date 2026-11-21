package com.prem.portfolio.superAdmin.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/super-admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

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
}


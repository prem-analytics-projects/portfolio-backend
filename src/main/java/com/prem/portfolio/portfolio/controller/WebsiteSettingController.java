
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.WebsiteSettingRequest;
import com.prem.portfolio.portfolio.dto.response.WebsiteSettingResponse;
import com.prem.portfolio.portfolio.service.WebsiteSettingService;

import jakarta.validation.Valid;

@RestController
public class WebsiteSettingController {

    private final WebsiteSettingService settingService;

    public WebsiteSettingController(WebsiteSettingService settingService) {
        this.settingService = settingService;
    }

    @PostMapping("/api/v1/admin/website-settings")
    public ResponseEntity<WebsiteSettingResponse> createSetting(
            @Valid @RequestBody WebsiteSettingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(settingService.createSetting(request));
    }

    @GetMapping("/api/v1/admin/website-settings")
    public ResponseEntity<List<WebsiteSettingResponse>> getAllSettings() {

        return ResponseEntity.ok(settingService.getAllSettings());
    }

    @GetMapping("/api/v1/admin/website-settings/{settingId}")
    public ResponseEntity<WebsiteSettingResponse> getSettingById(
            @PathVariable Long settingId) {

        return ResponseEntity.ok(settingService.getSettingById(settingId));
    }

    @PutMapping("/api/v1/admin/website-settings/{settingId}")
    public ResponseEntity<WebsiteSettingResponse> updateSetting(
            @PathVariable Long settingId,
            @Valid @RequestBody WebsiteSettingRequest request) {

        return ResponseEntity.ok(
                settingService.updateSetting(settingId, request));
    }

    @DeleteMapping("/api/v1/admin/website-settings/{settingId}")
    public ResponseEntity<Void> deleteSetting(
            @PathVariable Long settingId) {

        settingService.deleteSetting(settingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/v1/public/website-settings")
    public ResponseEntity<List<WebsiteSettingResponse>> getPublicSettings() {

        return ResponseEntity.ok(settingService.getPublicSettings());
    }
}

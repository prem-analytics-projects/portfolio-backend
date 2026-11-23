
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.prem.portfolio.portfolio.dto.request.ProfileRequest;
import com.prem.portfolio.portfolio.dto.response.ProfileResponse;
import com.prem.portfolio.portfolio.service.ProfileService;

@RestController
@RequestMapping("/api/v1/admin/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Create a new profile
    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(
            @Valid @RequestBody ProfileRequest request) {

        ProfileResponse response = profileService.createProfile(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all profiles
    @GetMapping
    public ResponseEntity<List<ProfileResponse>> getAllProfiles() {

        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    // Get profile by ID
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> getProfileById(
            @PathVariable Long profileId) {

        return ResponseEntity.ok(
                profileService.getProfileById(profileId));
    }

    // Update an existing profile
    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileRequest request) {

        return ResponseEntity.ok(
                profileService.updateProfile(profileId, request));
    }

    // Soft delete a profile
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable Long profileId) {

        profileService.deleteProfile(profileId);

        return ResponseEntity.noContent().build();
    }
}

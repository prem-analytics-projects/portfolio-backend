
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.prem.portfolio.portfolio.dto.request.ProfileRequest;
import com.prem.portfolio.portfolio.dto.response.ProfileResponse;
import com.prem.portfolio.portfolio.service.ProfileService;
import com.prem.portfolio.portfolio.dto.response.PageResponse;
@RestController
@RequestMapping("/api/v1/admin/profiles")
public class ProfileController {

    private final ProfileService profileService;

    private static final List<String> ALLOWED_SORT_FIELDS = List.of(
            "profileId",
            "fullName",
            "email",
            "createdAt",
            "updatedAt"
    );

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // CREATE PROFILE
    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(
            @Valid @RequestBody ProfileRequest request) {

        ProfileResponse response = profileService.createProfile(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL PROFILES WITH SEARCH, FILTERING, PAGINATION AND SORTING
    
@GetMapping
public ResponseEntity<PageResponse<ProfileResponse>> getAllProfiles(

        @RequestParam(defaultValue = "0") int page,

        @RequestParam(defaultValue = "10") int size,

        @RequestParam(defaultValue = "createdAt") String sortBy,

        @RequestParam(defaultValue = "desc") String direction,

        @RequestParam(required = false) String search,

        @RequestParam(required = false) Boolean active) {

    if (page < 0) {
        throw new IllegalArgumentException(
                "Page number cannot be negative."
        );
    }

    if (size < 1 || size > 100) {
        throw new IllegalArgumentException(
                "Page size must be between 1 and 100."
        );
    }

    if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
        throw new IllegalArgumentException(
                "Invalid sort field: " + sortBy
        );
    }

    Sort.Direction sortDirection;

    try {
        sortDirection = Sort.Direction.fromString(direction);
    } catch (IllegalArgumentException exception) {
        throw new IllegalArgumentException(
                "Direction must be either asc or desc."
        );
    }

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(sortDirection, sortBy)
    );

    Page<ProfileResponse> profiles =
            profileService.getAllProfiles(
                    search,
                    active,
                    pageable
            );

    return ResponseEntity.ok(
            new PageResponse<>(profiles)
    );
}

    // GET PROFILE BY ID
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> getProfileById(
            @PathVariable Long profileId) {

        return ResponseEntity.ok(
                profileService.getProfileById(profileId)
        );
    }

    // UPDATE PROFILE
    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileRequest request) {

        return ResponseEntity.ok(
                profileService.updateProfile(profileId, request)
        );
    }

    // SOFT DELETE PROFILE
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable Long profileId) {

        profileService.deleteProfile(profileId);

        return ResponseEntity.noContent().build();
    }
}

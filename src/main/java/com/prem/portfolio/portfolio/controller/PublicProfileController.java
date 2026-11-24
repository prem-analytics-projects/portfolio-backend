
package com.prem.portfolio.portfolio.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prem.portfolio.portfolio.dto.response.ProfileResponse;
import com.prem.portfolio.portfolio.dto.response.PublicProfileResponse;
import com.prem.portfolio.portfolio.service.ProfileService;

@RestController
@RequestMapping("/api/v1/public/profiles")
public class PublicProfileController {

    private final ProfileService profileService;

    public PublicProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // GET ACTIVE PROFILES FOR PUBLIC WEBSITE
    @GetMapping
    public ResponseEntity<Page<PublicProfileResponse>> getActiveProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

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

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        // Force active=true. Public visitors cannot retrieve inactive profiles.
        Page<ProfileResponse> profiles =
                profileService.getAllProfiles(
                        null,
                        true,
                        pageable
                );

        Page<PublicProfileResponse> response = profiles.map(
                this::toPublicResponse
        );

        return ResponseEntity.ok(response);
    }

    // MAP ADMIN RESPONSE TO PUBLIC RESPONSE
    private PublicProfileResponse toPublicResponse(
            ProfileResponse profile) {

        PublicProfileResponse response = new PublicProfileResponse();

        response.setProfileId(profile.getProfileId());
        response.setFullName(profile.getFullName());
        response.setProfessionalTitle(profile.getProfessionalTitle());
        response.setEmail(profile.getEmail());
        response.setPhone(profile.getPhone());
        response.setLocation(profile.getLocation());
        response.setShortDescription(profile.getShortDescription());
        response.setProfileImageUrl(profile.getProfileImageUrl());
        response.setResumeUrl(profile.getResumeUrl());

        return response;
    }
}

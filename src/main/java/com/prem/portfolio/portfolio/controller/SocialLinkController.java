
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.SocialLinkRequest;
import com.prem.portfolio.portfolio.dto.response.SocialLinkResponse;
import com.prem.portfolio.portfolio.service.SocialLinkService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/social-links")
public class SocialLinkController {

    private final SocialLinkService socialLinkService;

    public SocialLinkController(SocialLinkService socialLinkService) {
        this.socialLinkService = socialLinkService;
    }

    @PostMapping
    public ResponseEntity<SocialLinkResponse> createSocialLink(
            @Valid @RequestBody SocialLinkRequest request) {

        SocialLinkResponse response =
                socialLinkService.createSocialLink(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SocialLinkResponse>> getSocialLinks(
            @RequestParam Long profileId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                socialLinkService.getSocialLinks(profileId, active)
        );
    }

    @GetMapping("/{socialLinkId}")
    public ResponseEntity<SocialLinkResponse> getSocialLinkById(
            @PathVariable Long socialLinkId) {

        return ResponseEntity.ok(
                socialLinkService.getSocialLinkById(socialLinkId)
        );
    }

    @PutMapping("/{socialLinkId}")
    public ResponseEntity<SocialLinkResponse> updateSocialLink(
            @PathVariable Long socialLinkId,
            @Valid @RequestBody SocialLinkRequest request) {

        return ResponseEntity.ok(
                socialLinkService.updateSocialLink(socialLinkId, request)
        );
    }

    @DeleteMapping("/{socialLinkId}")
    public ResponseEntity<Void> deleteSocialLink(
            @PathVariable Long socialLinkId) {

        socialLinkService.deleteSocialLink(socialLinkId);

        return ResponseEntity.noContent().build();
    }
}

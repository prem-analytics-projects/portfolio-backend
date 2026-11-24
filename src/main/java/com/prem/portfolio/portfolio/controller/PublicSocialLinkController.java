
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.PublicSocialLinkResponse;
import com.prem.portfolio.portfolio.dto.response.SocialLinkResponse;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.SocialLinkService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/social-links")
public class PublicSocialLinkController {

    private final SocialLinkService socialLinkService;
    private final ProfileRepository profileRepository;

    public PublicSocialLinkController(
            SocialLinkService socialLinkService,
            ProfileRepository profileRepository) {

        this.socialLinkService = socialLinkService;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public List<PublicSocialLinkResponse> getPublicSocialLinks(
            @RequestParam Long profileId) {

        if (profileId == null || profileId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A valid profileId is required"
            );
        }

        boolean activeProfileExists =
                profileRepository
                        .findByProfileIdAndActiveTrue(profileId)
                        .isPresent();

        if (!activeProfileExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Active profile not found"
            );
        }

        List<SocialLinkResponse> socialLinks =
                socialLinkService.getSocialLinks(profileId, true);

        return socialLinks.stream()
                .map(this::toPublicResponse)
                .toList();
    }

    private PublicSocialLinkResponse toPublicResponse(
            SocialLinkResponse response) {

        PublicSocialLinkResponse publicResponse =
                new PublicSocialLinkResponse();

        publicResponse.setSocialLinkId(response.getSocialLinkId());
        publicResponse.setProfileId(response.getProfileId());
        publicResponse.setPlatformName(response.getPlatformName());
        publicResponse.setLinkUrl(response.getLinkUrl());
        publicResponse.setIconName(response.getIconName());
        publicResponse.setDisplayOrder(response.getDisplayOrder());

        return publicResponse;
    }
}

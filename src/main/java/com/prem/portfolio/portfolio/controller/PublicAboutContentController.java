
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.AboutContentResponse;
import com.prem.portfolio.portfolio.dto.response.PublicAboutContentResponse;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.AboutContentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/about-content")
public class PublicAboutContentController {

    private final AboutContentService aboutContentService;
    private final ProfileRepository profileRepository;

    public PublicAboutContentController(
            AboutContentService aboutContentService,
            ProfileRepository profileRepository
    ) {
        this.aboutContentService = aboutContentService;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ResponseEntity<List<PublicAboutContentResponse>> getPublicAboutContent(
            @RequestParam Long profileId
    ) {

        if (profileId == null || profileId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A valid profileId is required"
            );
        }

        // Do not expose content belonging to an inactive profile.
        profileRepository.findByProfileIdAndActiveTrue(profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Active profile not found"
                ));

        // Always return active About content only.
        List<AboutContentResponse> aboutContentList =
                aboutContentService.getAllAboutContent(profileId, true);

        List<PublicAboutContentResponse> response = aboutContentList.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicAboutContentResponse toPublicResponse(
            AboutContentResponse source
    ) {
        PublicAboutContentResponse response =
                new PublicAboutContentResponse();

        response.setAboutId(source.getAboutId());
        response.setProfileId(source.getProfileId());
        response.setTitle(source.getTitle());
        response.setDescription(source.getDescription());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}

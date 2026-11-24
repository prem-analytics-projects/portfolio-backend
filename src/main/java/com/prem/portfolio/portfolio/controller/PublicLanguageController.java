
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.LanguageResponse;
import com.prem.portfolio.portfolio.dto.response.PublicLanguageResponse;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.LanguageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/languages")
public class PublicLanguageController {

    private final LanguageService languageService;
    private final ProfileRepository profileRepository;

    public PublicLanguageController(
            LanguageService languageService,
            ProfileRepository profileRepository) {
        this.languageService = languageService;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ResponseEntity<List<PublicLanguageResponse>> getPublicLanguages(
            @RequestParam Long profileId) {

        if (profileId == null || profileId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A valid profileId is required"
            );
        }

        // Only expose languages belonging to an active profile.
        profileRepository.findByProfileIdAndActiveTrue(profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Active profile not found"
                ));

        // Force active=true so inactive languages are never exposed.
        List<LanguageResponse> languages =
                languageService.getLanguages(profileId, true);

        List<PublicLanguageResponse> response = languages.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicLanguageResponse toPublicResponse(
            LanguageResponse source) {

        PublicLanguageResponse response = new PublicLanguageResponse();

        response.setLanguageId(source.getLanguageId());
        response.setProfileId(source.getProfileId());
        response.setLanguageName(source.getLanguageName());
        response.setProficiencyLevel(source.getProficiencyLevel());
        response.setProficiencyLabel(source.getProficiencyLabel());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}

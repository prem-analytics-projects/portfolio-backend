
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.EducationResponse;
import com.prem.portfolio.portfolio.dto.response.PublicEducationResponse;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.EducationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/educations")
public class PublicEducationController {

    private final EducationService educationService;
    private final ProfileRepository profileRepository;

    public PublicEducationController(
            EducationService educationService,
            ProfileRepository profileRepository
    ) {
        this.educationService = educationService;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ResponseEntity<List<PublicEducationResponse>> getPublicEducations(
            @RequestParam Long profileId
    ) {

        if (profileId == null || profileId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A valid profileId is required"
            );
        }

        // Confirm that the requested profile is active.
        profileRepository.findByProfileIdAndActiveTrue(profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Active profile not found"
                ));

        // Force active=true so inactive education records are never returned.
        List<EducationResponse> educationList =
                educationService.getEducations(profileId, true);

        List<PublicEducationResponse> response = educationList.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicEducationResponse toPublicResponse(
            EducationResponse source
    ) {
        PublicEducationResponse response =
                new PublicEducationResponse();

        response.setEducationId(source.getEducationId());
        response.setProfileId(source.getProfileId());
        response.setInstitutionName(source.getInstitutionName());
        response.setDegree(source.getDegree());
        response.setFieldOfStudy(source.getFieldOfStudy());
        response.setStartDate(source.getStartDate());
        response.setEndDate(source.getEndDate());
        response.setGrade(source.getGrade());
        response.setDescription(source.getDescription());
        response.setCertificateUrl(source.getCertificateUrl());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}

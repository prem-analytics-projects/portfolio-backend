
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.PublicSkillResponse;
import com.prem.portfolio.portfolio.dto.response.SkillResponse;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.SkillService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/skills")
public class PublicSkillController {

    private final SkillService skillService;
    private final ProfileRepository profileRepository;

    public PublicSkillController(
            SkillService skillService,
            ProfileRepository profileRepository
    ) {
        this.skillService = skillService;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ResponseEntity<List<PublicSkillResponse>> getPublicSkills(
            @RequestParam Long profileId
    ) {

        if (profileId == null || profileId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A valid profileId is required"
            );
        }

        // Verify that the requested profile is active.
        profileRepository.findByProfileIdAndActiveTrue(profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Active profile not found"
                ));

        // Always return active skills only.
        List<SkillResponse> skills =
                skillService.getAllSkills(profileId, null, true);

        List<PublicSkillResponse> response = skills.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicSkillResponse toPublicResponse(SkillResponse source) {

        PublicSkillResponse response = new PublicSkillResponse();

        response.setSkillId(source.getSkillId());
        response.setProfileId(source.getProfileId());
        response.setSkillCategoryId(source.getSkillCategoryId());
        response.setSkillName(source.getSkillName());
        response.setProficiencyLevel(source.getProficiencyLevel());
        response.setYearsExperience(source.getYearsExperience());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}

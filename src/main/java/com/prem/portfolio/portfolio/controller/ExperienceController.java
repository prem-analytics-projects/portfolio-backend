
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.ExperienceRequest;
import com.prem.portfolio.portfolio.dto.response.ExperienceResponse;
import com.prem.portfolio.portfolio.service.ExperienceService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> createExperience(
            @Valid @RequestBody ExperienceRequest request) {

        ExperienceResponse response =
                experienceService.createExperience(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getExperiences(
            @RequestParam Long profileId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                experienceService.getExperiences(profileId, active)
        );
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> getExperienceById(
            @PathVariable Long experienceId) {

        return ResponseEntity.ok(
                experienceService.getExperienceById(experienceId)
        );
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long experienceId,
            @Valid @RequestBody ExperienceRequest request) {

        return ResponseEntity.ok(
                experienceService.updateExperience(experienceId, request)
        );
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long experienceId) {

        experienceService.deleteExperience(experienceId);

        return ResponseEntity.noContent().build();
    }
}

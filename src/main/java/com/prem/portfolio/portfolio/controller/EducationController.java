
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.EducationRequest;
import com.prem.portfolio.portfolio.dto.response.EducationResponse;
import com.prem.portfolio.portfolio.service.EducationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/educations")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    // CREATE EDUCATION
    @PostMapping
    public ResponseEntity<EducationResponse> createEducation(
            @Valid @RequestBody EducationRequest request) {

        EducationResponse response =
                educationService.createEducation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL EDUCATION RECORDS FOR A PROFILE
    @GetMapping
    public ResponseEntity<List<EducationResponse>> getEducations(
            @RequestParam Long profileId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                educationService.getEducations(profileId, active)
        );
    }

    // GET EDUCATION BY ID
    @GetMapping("/{educationId}")
    public ResponseEntity<EducationResponse> getEducationById(
            @PathVariable Long educationId) {

        return ResponseEntity.ok(
                educationService.getEducationById(educationId)
        );
    }

    // UPDATE EDUCATION
    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable Long educationId,
            @Valid @RequestBody EducationRequest request) {

        return ResponseEntity.ok(
                educationService.updateEducation(educationId, request)
        );
    }

    // SOFT DELETE EDUCATION
    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable Long educationId) {

        educationService.deleteEducation(educationId);

        return ResponseEntity.noContent().build();
    }
}

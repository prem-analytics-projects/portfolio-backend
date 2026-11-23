
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.ProjectTechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectTechnologyResponse;
import com.prem.portfolio.portfolio.service.ProjectTechnologyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/projects/{projectId}/technologies")
public class ProjectTechnologyController {

    private final ProjectTechnologyService projectTechnologyService;

    public ProjectTechnologyController(
            ProjectTechnologyService projectTechnologyService
    ) {
        this.projectTechnologyService = projectTechnologyService;
    }

    @PostMapping
    public ResponseEntity<ProjectTechnologyResponse> addTechnology(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectTechnologyRequest request
    ) {

        ProjectTechnologyResponse response =
                projectTechnologyService.addTechnology(projectId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectTechnologyResponse>> getProjectTechnologies(
            @PathVariable Long projectId
    ) {

        return ResponseEntity.ok(
                projectTechnologyService.getProjectTechnologies(projectId)
        );
    }

    @DeleteMapping("/{technologyId}")
    public ResponseEntity<Void> removeTechnology(
            @PathVariable Long projectId,
            @PathVariable Long technologyId
    ) {

        projectTechnologyService.removeTechnology(projectId, technologyId);

        return ResponseEntity.noContent().build();
    }
}

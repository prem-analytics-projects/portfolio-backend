
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.ProjectRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectResponse;
import com.prem.portfolio.portfolio.service.ProjectService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request
    ) {
        ProjectResponse response = projectService.createProject(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @RequestParam Long profileId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean featured
    ) {
        return ResponseEntity.ok(
                projectService.getProjects(profileId, active, featured)
        );
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                projectService.getProjectById(projectId)
        );
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request
    ) {
        return ResponseEntity.ok(
                projectService.updateProject(projectId, request)
        );
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId
    ) {
        projectService.deleteProject(projectId);

        return ResponseEntity.noContent().build();
    }
}

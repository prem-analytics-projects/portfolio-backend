
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.ProjectMediaRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectMediaResponse;
import com.prem.portfolio.portfolio.service.ProjectMediaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/projects/{projectId}/media")
public class ProjectMediaController {

    private final ProjectMediaService projectMediaService;

    public ProjectMediaController(
            ProjectMediaService projectMediaService
    ) {
        this.projectMediaService = projectMediaService;
    }

    @PostMapping
    public ResponseEntity<ProjectMediaResponse> addMedia(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectMediaRequest request
    ) {

        ProjectMediaResponse response =
                projectMediaService.addMedia(projectId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMediaResponse>> getProjectMedia(
            @PathVariable Long projectId,
            @RequestParam(required = false) Boolean active
    ) {

        return ResponseEntity.ok(
                projectMediaService.getProjectMedia(projectId, active)
        );
    }

    @GetMapping("/{projectMediaId}")
    public ResponseEntity<ProjectMediaResponse> getMediaById(
            @PathVariable Long projectId,
            @PathVariable Long projectMediaId
    ) {

        return ResponseEntity.ok(
                projectMediaService.getMediaById(
                        projectId,
                        projectMediaId
                )
        );
    }

    @PutMapping("/{projectMediaId}")
    public ResponseEntity<ProjectMediaResponse> updateMedia(
            @PathVariable Long projectId,
            @PathVariable Long projectMediaId,
            @Valid @RequestBody ProjectMediaRequest request
    ) {

        return ResponseEntity.ok(
                projectMediaService.updateMedia(
                        projectId,
                        projectMediaId,
                        request
                )
        );
    }

    @DeleteMapping("/{projectMediaId}")
    public ResponseEntity<Void> deleteMedia(
            @PathVariable Long projectId,
            @PathVariable Long projectMediaId
    ) {

        projectMediaService.deleteMedia(projectId, projectMediaId);

        return ResponseEntity.noContent().build();
    }
}

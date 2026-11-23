
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.ProjectImpactMetricRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectImpactMetricResponse;
import com.prem.portfolio.portfolio.service.ProjectImpactMetricService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/projects/{projectId}/impact-metrics")
public class ProjectImpactMetricController {

    private final ProjectImpactMetricService metricService;

    public ProjectImpactMetricController(
            ProjectImpactMetricService metricService
    ) {
        this.metricService = metricService;
    }

    @PostMapping
    public ResponseEntity<ProjectImpactMetricResponse> createMetric(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectImpactMetricRequest request
    ) {

        request.setProjectId(projectId);

        ProjectImpactMetricResponse response =
                metricService.createMetric(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectImpactMetricResponse>> getMetrics(
            @PathVariable Long projectId,
            @RequestParam(required = false) Boolean active
    ) {

        return ResponseEntity.ok(
                metricService.getMetrics(projectId, active)
        );
    }

    @GetMapping("/{impactMetricId}")
    public ResponseEntity<ProjectImpactMetricResponse> getMetricById(
            @PathVariable Long projectId,
            @PathVariable Long impactMetricId
    ) {

        ProjectImpactMetricResponse response =
                metricService.getMetricById(impactMetricId);

        if (!response.getProjectId().equals(projectId)) {
            throw new IllegalArgumentException(
                    "Impact metric does not belong to this project"
            );
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{impactMetricId}")
    public ResponseEntity<ProjectImpactMetricResponse> updateMetric(
            @PathVariable Long projectId,
            @PathVariable Long impactMetricId,
            @Valid @RequestBody ProjectImpactMetricRequest request
    ) {

        request.setProjectId(projectId);

        ProjectImpactMetricResponse response =
                metricService.updateMetric(impactMetricId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{impactMetricId}")
    public ResponseEntity<Void> deleteMetric(
            @PathVariable Long projectId,
            @PathVariable Long impactMetricId
    ) {

        ProjectImpactMetricResponse metric =
                metricService.getMetricById(impactMetricId);

        if (!metric.getProjectId().equals(projectId)) {
            throw new IllegalArgumentException(
                    "Impact metric does not belong to this project"
            );
        }

        metricService.deleteMetric(impactMetricId);

        return ResponseEntity.noContent().build();
    }
}

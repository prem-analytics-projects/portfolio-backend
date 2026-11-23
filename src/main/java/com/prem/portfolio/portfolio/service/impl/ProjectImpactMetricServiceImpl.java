
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.portfolio.dto.request.ProjectImpactMetricRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectImpactMetricResponse;
import com.prem.portfolio.portfolio.entity.Project;
import com.prem.portfolio.portfolio.entity.ProjectImpactMetric;
import com.prem.portfolio.portfolio.mapper.ProjectImpactMetricMapper;
import com.prem.portfolio.portfolio.repository.ProjectImpactMetricRepository;
import com.prem.portfolio.portfolio.repository.ProjectRepository;
import com.prem.portfolio.portfolio.service.ProjectImpactMetricService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectImpactMetricServiceImpl
        implements ProjectImpactMetricService {

    private final ProjectImpactMetricRepository metricRepository;
    private final ProjectRepository projectRepository;
    private final ProjectImpactMetricMapper metricMapper;

    public ProjectImpactMetricServiceImpl(
            ProjectImpactMetricRepository metricRepository,
            ProjectRepository projectRepository,
            ProjectImpactMetricMapper metricMapper
    ) {
        this.metricRepository = metricRepository;
        this.projectRepository = projectRepository;
        this.metricMapper = metricMapper;
    }

    @Override
    public ProjectImpactMetricResponse createMetric(
            ProjectImpactMetricRequest request
    ) {

        validateActiveProject(request.getProjectId());

        ProjectImpactMetric metric = metricMapper.toEntity(request);

        ProjectImpactMetric savedMetric =
                metricRepository.save(metric);

        return metricMapper.toResponse(savedMetric);
    }

    @Override
    public ProjectImpactMetricResponse updateMetric(
            Long impactMetricId,
            ProjectImpactMetricRequest request
    ) {

        ProjectImpactMetric metric = metricRepository
                .findByImpactMetricIdAndActiveTrue(impactMetricId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active impact metric not found"
                        )
                );

        validateActiveProject(request.getProjectId());

        metricMapper.updateEntity(metric, request);

        ProjectImpactMetric updatedMetric =
                metricRepository.save(metric);

        return metricMapper.toResponse(updatedMetric);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectImpactMetricResponse getMetricById(
            Long impactMetricId
    ) {

        ProjectImpactMetric metric = metricRepository
                .findByImpactMetricIdAndActiveTrue(impactMetricId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active impact metric not found"
                        )
                );

        return metricMapper.toResponse(metric);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectImpactMetricResponse> getMetrics(
            Long projectId,
            Boolean active
    ) {

        validateProjectExists(projectId);

        return metricRepository
                .findMetrics(projectId, active)
                .stream()
                .map(metricMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteMetric(Long impactMetricId) {

        ProjectImpactMetric metric = metricRepository
                .findByImpactMetricIdAndActiveTrue(impactMetricId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active impact metric not found"
                        )
                );

        metric.setActive(false);

        metricRepository.save(metric);
    }

    private void validateActiveProject(Long projectId) {

        Project project = projectRepository
                .findByProjectIdAndActiveTrue(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active project not found"
                        )
                );

        if (!Boolean.TRUE.equals(project.getActive())) {
            throw new IllegalArgumentException(
                    "Cannot add metrics to an inactive project"
            );
        }
    }

    private void validateProjectExists(Long projectId) {

        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project not found");
        }
    }
}

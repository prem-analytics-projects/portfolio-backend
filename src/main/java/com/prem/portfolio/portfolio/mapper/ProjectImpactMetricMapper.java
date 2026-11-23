
package com.prem.portfolio.portfolio.mapper;

import com.prem.portfolio.portfolio.dto.request.ProjectImpactMetricRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectImpactMetricResponse;
import com.prem.portfolio.portfolio.entity.ProjectImpactMetric;

import org.springframework.stereotype.Component;

@Component
public class ProjectImpactMetricMapper {

    public ProjectImpactMetric toEntity(
            ProjectImpactMetricRequest request
    ) {

        ProjectImpactMetric metric = new ProjectImpactMetric();

        metric.setProjectId(request.getProjectId());
        metric.setMetricLabel(request.getMetricLabel().trim());
        metric.setMetricValue(request.getMetricValue().trim());
        metric.setMetricUnit(
                request.getMetricUnit() == null
                        ? null
                        : request.getMetricUnit().trim()
        );
        metric.setDescription(request.getDescription());
        metric.setDisplayOrder(request.getDisplayOrder());
        metric.setActive(request.getActive());

        return metric;
    }

    public void updateEntity(
            ProjectImpactMetric metric,
            ProjectImpactMetricRequest request
    ) {

        metric.setProjectId(request.getProjectId());
        metric.setMetricLabel(request.getMetricLabel().trim());
        metric.setMetricValue(request.getMetricValue().trim());
        metric.setMetricUnit(
                request.getMetricUnit() == null
                        ? null
                        : request.getMetricUnit().trim()
        );
        metric.setDescription(request.getDescription());
        metric.setDisplayOrder(request.getDisplayOrder());
        metric.setActive(request.getActive());
    }

    public ProjectImpactMetricResponse toResponse(
            ProjectImpactMetric metric
    ) {

        ProjectImpactMetricResponse response =
                new ProjectImpactMetricResponse();

        response.setImpactMetricId(metric.getImpactMetricId());
        response.setProjectId(metric.getProjectId());
        response.setMetricLabel(metric.getMetricLabel());
        response.setMetricValue(metric.getMetricValue());
        response.setMetricUnit(metric.getMetricUnit());
        response.setDescription(metric.getDescription());
        response.setDisplayOrder(metric.getDisplayOrder());
        response.setActive(metric.getActive());
        response.setCreatedAt(metric.getCreatedAt());
        response.setUpdatedAt(metric.getUpdatedAt());
        response.setCreatedBy(metric.getCreatedBy());
        response.setUpdatedBy(metric.getUpdatedBy());

        return response;
    }
}


package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.ProjectImpactMetricRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectImpactMetricResponse;

import java.util.List;

public interface ProjectImpactMetricService {

    ProjectImpactMetricResponse createMetric(
            ProjectImpactMetricRequest request
    );

    ProjectImpactMetricResponse updateMetric(
            Long impactMetricId,
            ProjectImpactMetricRequest request
    );

    ProjectImpactMetricResponse getMetricById(Long impactMetricId);

    List<ProjectImpactMetricResponse> getMetrics(
            Long projectId,
            Boolean active
    );

    void deleteMetric(Long impactMetricId);
}

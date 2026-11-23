
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.ProjectTechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectTechnologyResponse;

public interface ProjectTechnologyService {

    ProjectTechnologyResponse addTechnology(
            Long projectId,
            ProjectTechnologyRequest request
    );

    List<ProjectTechnologyResponse> getProjectTechnologies(
            Long projectId
    );

    void removeTechnology(
            Long projectId,
            Long technologyId
    );
}

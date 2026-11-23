
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.ProjectRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long projectId, ProjectRequest request);

    ProjectResponse getProjectById(Long projectId);

    List<ProjectResponse> getProjects(
            Long profileId,
            Boolean active,
            Boolean featured
    );

    void deleteProject(Long projectId);
}

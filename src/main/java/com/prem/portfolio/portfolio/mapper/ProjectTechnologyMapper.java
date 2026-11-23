
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.entity.ProjectTechnology;
import com.prem.portfolio.portfolio.entity.Technology;
import com.prem.portfolio.portfolio.dto.response.ProjectTechnologyResponse;

@Component
public class ProjectTechnologyMapper {

    public ProjectTechnology toEntity(
            Long projectId,
            Long technologyId
    ) {

        return new ProjectTechnology(projectId, technologyId);
    }

    public ProjectTechnologyResponse toResponse(
            ProjectTechnology projectTechnology,
            Technology technology
    ) {

        ProjectTechnologyResponse response =
                new ProjectTechnologyResponse();

        response.setProjectId(projectTechnology.getProjectId());
        response.setTechnologyId(projectTechnology.getTechnologyId());
        response.setTechnologyName(technology.getTechnologyName());
        response.setTechnologyType(technology.getTechnologyType());

        return response;
    }
}

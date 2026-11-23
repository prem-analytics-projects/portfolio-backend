
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.ProjectRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectResponse;
import com.prem.portfolio.portfolio.entity.Project;

@Component
public class ProjectMapper {

    // Convert request DTO to entity
    public Project toEntity(ProjectRequest request) {

        Project project = new Project();

        project.setProfileId(request.getProfileId());
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setProblemStatement(request.getProblemStatement());
        project.setSolutionSummary(request.getSolutionSummary());
        project.setImpactSummary(request.getImpactSummary());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setThumbnailMediaId(request.getThumbnailMediaId());
        project.setFeatured(request.getFeatured());
        project.setActive(request.getActive());
        project.setDisplayOrder(request.getDisplayOrder());

        return project;
    }

    // Update existing entity using request DTO
    public void updateEntity(Project project, ProjectRequest request) {

        project.setProfileId(request.getProfileId());
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setProblemStatement(request.getProblemStatement());
        project.setSolutionSummary(request.getSolutionSummary());
        project.setImpactSummary(request.getImpactSummary());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setThumbnailMediaId(request.getThumbnailMediaId());
        project.setFeatured(request.getFeatured());
        project.setActive(request.getActive());
        project.setDisplayOrder(request.getDisplayOrder());
    }

    // Convert entity to response DTO
    public ProjectResponse toResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setProjectId(project.getProjectId());
        response.setProfileId(project.getProfileId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setProblemStatement(project.getProblemStatement());
        response.setSolutionSummary(project.getSolutionSummary());
        response.setImpactSummary(project.getImpactSummary());
        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());
        response.setProjectUrl(project.getProjectUrl());
        response.setGithubUrl(project.getGithubUrl());
        response.setThumbnailMediaId(project.getThumbnailMediaId());
        response.setFeatured(project.getFeatured());
        response.setActive(project.getActive());
        response.setDisplayOrder(project.getDisplayOrder());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        response.setCreatedBy(project.getCreatedBy());
        response.setUpdatedBy(project.getUpdatedBy());

        return response;
    }
}

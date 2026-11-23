
package com.prem.portfolio.portfolio.mapper;

import com.prem.portfolio.portfolio.dto.request.ProjectMediaRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectMediaResponse;
import com.prem.portfolio.portfolio.entity.ProjectMedia;

import org.springframework.stereotype.Component;

@Component
public class ProjectMediaMapper {

    public ProjectMedia toEntity(
            Long projectId,
            ProjectMediaRequest request
    ) {

        ProjectMedia projectMedia = new ProjectMedia();

        projectMedia.setProjectId(projectId);
        projectMedia.setMediaId(request.getMediaId());
        projectMedia.setCaption(
                request.getCaption() == null
                        ? null
                        : request.getCaption().trim()
        );
        projectMedia.setDisplayOrder(request.getDisplayOrder());
        projectMedia.setActive(true);

        return projectMedia;
    }

    public void updateEntity(
            ProjectMedia projectMedia,
            ProjectMediaRequest request
    ) {

        projectMedia.setMediaId(request.getMediaId());
        projectMedia.setCaption(
                request.getCaption() == null
                        ? null
                        : request.getCaption().trim()
        );
        projectMedia.setDisplayOrder(request.getDisplayOrder());
    }

    public ProjectMediaResponse toResponse(ProjectMedia projectMedia) {

        ProjectMediaResponse response = new ProjectMediaResponse();

        response.setProjectMediaId(projectMedia.getProjectMediaId());
        response.setProjectId(projectMedia.getProjectId());
        response.setMediaId(projectMedia.getMediaId());
        response.setCaption(projectMedia.getCaption());
        response.setDisplayOrder(projectMedia.getDisplayOrder());
        response.setActive(projectMedia.getActive());
        response.setCreatedAt(projectMedia.getCreatedAt());
        response.setCreatedBy(projectMedia.getCreatedBy());

        return response;
    }
}

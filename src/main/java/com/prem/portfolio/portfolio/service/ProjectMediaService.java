
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.ProjectMediaRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectMediaResponse;

import java.util.List;

public interface ProjectMediaService {

    ProjectMediaResponse addMedia(
            Long projectId,
            ProjectMediaRequest request
    );

    ProjectMediaResponse updateMedia(
            Long projectId,
            Long projectMediaId,
            ProjectMediaRequest request
    );

    ProjectMediaResponse getMediaById(
            Long projectId,
            Long projectMediaId
    );

    List<ProjectMediaResponse> getProjectMedia(
            Long projectId,
            Boolean active
    );

    void deleteMedia(
            Long projectId,
            Long projectMediaId
    );
}

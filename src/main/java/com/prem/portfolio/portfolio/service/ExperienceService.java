
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.ExperienceRequest;
import com.prem.portfolio.portfolio.dto.response.ExperienceResponse;

import java.util.List;

public interface ExperienceService {

    ExperienceResponse createExperience(ExperienceRequest request);

    ExperienceResponse updateExperience(
            Long experienceId,
            ExperienceRequest request
    );

    ExperienceResponse getExperienceById(Long experienceId);

    List<ExperienceResponse> getExperiences(
            Long profileId,
            Boolean active
    );

    void deleteExperience(Long experienceId);
}

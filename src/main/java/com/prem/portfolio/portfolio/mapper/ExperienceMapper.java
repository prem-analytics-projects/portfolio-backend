
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.ExperienceRequest;
import com.prem.portfolio.portfolio.dto.response.ExperienceResponse;
import com.prem.portfolio.portfolio.entity.Experience;

@Component
public class ExperienceMapper {

    public Experience toEntity(ExperienceRequest request) {
        Experience experience = new Experience();
        updateEntity(experience, request);
        return experience;
    }

    public void updateEntity(
            Experience experience,
            ExperienceRequest request) {

        experience.setProfileId(request.getProfileId());
        experience.setCompanyId(request.getCompanyId());
        experience.setJobTitle(request.getJobTitle());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());

        experience.setCurrent(
                request.getCurrent() != null
                        ? request.getCurrent()
                        : false
        );

        experience.setDescription(request.getDescription());

        experience.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );

        experience.setActive(
                request.getActive() != null
                        ? request.getActive()
                        : true
        );
    }

    public ExperienceResponse toResponse(Experience experience) {

        ExperienceResponse response = new ExperienceResponse();

        response.setExperienceId(experience.getExperienceId());
        response.setProfileId(experience.getProfileId());
        response.setCompanyId(experience.getCompanyId());
        response.setJobTitle(experience.getJobTitle());
        response.setEmploymentType(experience.getEmploymentType());
        response.setStartDate(experience.getStartDate());
        response.setEndDate(experience.getEndDate());
        response.setCurrent(experience.getCurrent());
        response.setDescription(experience.getDescription());
        response.setDisplayOrder(experience.getDisplayOrder());
        response.setActive(experience.getActive());
        response.setCreatedAt(experience.getCreatedAt());
        response.setUpdatedAt(experience.getUpdatedAt());
        response.setCreatedBy(experience.getCreatedBy());
        response.setUpdatedBy(experience.getUpdatedBy());

        return response;
    }
}

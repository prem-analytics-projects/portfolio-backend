
package com.prem.portfolio.portfolio.mapper;

import com.prem.portfolio.portfolio.dto.request.EducationRequest;
import com.prem.portfolio.portfolio.dto.response.EducationResponse;
import com.prem.portfolio.portfolio.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public Education toEntity(EducationRequest request) {
        Education education = new Education();

        education.setProfileId(request.getProfileId());
        education.setInstitutionName(request.getInstitutionName());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setGrade(request.getGrade());
        education.setDescription(request.getDescription());
        education.setCertificateMediaId(request.getCertificateMediaId());
        education.setDisplayOrder(request.getDisplayOrder());
        education.setActive(request.getActive());

        return education;
    }

    public void updateEntity(Education education, EducationRequest request) {
        education.setProfileId(request.getProfileId());
        education.setInstitutionName(request.getInstitutionName());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setGrade(request.getGrade());
        education.setDescription(request.getDescription());
        education.setCertificateMediaId(request.getCertificateMediaId());
        education.setDisplayOrder(request.getDisplayOrder());
        education.setActive(request.getActive());
    }

    public EducationResponse toResponse(Education education) {
        EducationResponse response = new EducationResponse();

        response.setEducationId(education.getEducationId());
        response.setProfileId(education.getProfileId());
        response.setInstitutionName(education.getInstitutionName());
        response.setDegree(education.getDegree());
        response.setFieldOfStudy(education.getFieldOfStudy());
        response.setStartDate(education.getStartDate());
        response.setEndDate(education.getEndDate());
        response.setGrade(education.getGrade());
        response.setDescription(education.getDescription());
        response.setCertificateMediaId(education.getCertificateMediaId());
        response.setDisplayOrder(education.getDisplayOrder());
        response.setActive(education.getActive());
        response.setCreatedAt(education.getCreatedAt());
        response.setUpdatedAt(education.getUpdatedAt());

        return response;
    }
}

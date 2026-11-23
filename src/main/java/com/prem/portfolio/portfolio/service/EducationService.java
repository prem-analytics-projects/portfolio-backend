
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.EducationRequest;
import com.prem.portfolio.portfolio.dto.response.EducationResponse;

import java.util.List;

public interface EducationService {

    EducationResponse createEducation(EducationRequest request);

    EducationResponse updateEducation(Long educationId, EducationRequest request);

    EducationResponse getEducationById(Long educationId);

    List<EducationResponse> getEducations(Long profileId, Boolean active);

    void deleteEducation(Long educationId);
}

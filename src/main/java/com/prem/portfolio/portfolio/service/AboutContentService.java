
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.AboutContentRequest;
import com.prem.portfolio.portfolio.dto.response.AboutContentResponse;

import java.util.List;

public interface AboutContentService {

    AboutContentResponse createAboutContent(AboutContentRequest request);

    AboutContentResponse updateAboutContent(
            Long aboutId,
            AboutContentRequest request
    );

    AboutContentResponse getAboutContentById(Long aboutId);

    List<AboutContentResponse> getAllAboutContent(
            Long profileId,
            Boolean active
    );

    void deleteAboutContent(Long aboutId);
}

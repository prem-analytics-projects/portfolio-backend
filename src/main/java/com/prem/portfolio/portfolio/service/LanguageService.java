
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.LanguageRequest;
import com.prem.portfolio.portfolio.dto.response.LanguageResponse;

import java.util.List;

public interface LanguageService {

    LanguageResponse createLanguage(LanguageRequest request);

    LanguageResponse updateLanguage(Long languageId, LanguageRequest request);

    LanguageResponse getLanguageById(Long languageId);

    List<LanguageResponse> getLanguages(Long profileId, Boolean active);

    void deleteLanguage(Long languageId);
}

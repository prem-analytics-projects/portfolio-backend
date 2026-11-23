
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.WebsiteSettingRequest;
import com.prem.portfolio.portfolio.dto.response.WebsiteSettingResponse;

public interface WebsiteSettingService {

    WebsiteSettingResponse createSetting(WebsiteSettingRequest request);

    WebsiteSettingResponse updateSetting(
            Long settingId,
            WebsiteSettingRequest request
    );

    List<WebsiteSettingResponse> getAllSettings();

    List<WebsiteSettingResponse> getPublicSettings();

    WebsiteSettingResponse getSettingById(Long settingId);

    void deleteSetting(Long settingId);
}

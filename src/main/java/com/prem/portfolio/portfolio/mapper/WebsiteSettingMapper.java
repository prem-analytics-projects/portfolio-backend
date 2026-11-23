
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.WebsiteSettingRequest;
import com.prem.portfolio.portfolio.dto.response.WebsiteSettingResponse;
import com.prem.portfolio.portfolio.entity.WebsiteSetting;

@Component
public class WebsiteSettingMapper {

    public WebsiteSetting toEntity(WebsiteSettingRequest request) {
        WebsiteSetting setting = new WebsiteSetting();

        setting.setSettingKey(request.getSettingKey().trim());
        setting.setSettingValue(request.getSettingValue());
        setting.setDescription(
                request.getDescription() == null
                        ? null
                        : request.getDescription().trim()
        );
        setting.setIsPublic(request.getIsPublic());

        return setting;
    }

    public void updateEntity(
            WebsiteSetting setting,
            WebsiteSettingRequest request) {

        setting.setSettingKey(request.getSettingKey().trim());
        setting.setSettingValue(request.getSettingValue());
        setting.setDescription(
                request.getDescription() == null
                        ? null
                        : request.getDescription().trim()
        );
        setting.setIsPublic(request.getIsPublic());
    }

    public WebsiteSettingResponse toResponse(WebsiteSetting setting) {
        WebsiteSettingResponse response = new WebsiteSettingResponse();

        response.setSettingId(setting.getSettingId());
        response.setSettingKey(setting.getSettingKey());
        response.setSettingValue(setting.getSettingValue());
        response.setDescription(setting.getDescription());
        response.setIsPublic(setting.getIsPublic());
        response.setUpdatedAt(setting.getUpdatedAt());
        response.setUpdatedBy(setting.getUpdatedBy());

        return response;
    }
}

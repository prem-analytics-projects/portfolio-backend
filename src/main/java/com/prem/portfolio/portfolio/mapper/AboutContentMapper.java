
package com.prem.portfolio.portfolio.mapper;

import com.prem.portfolio.portfolio.dto.request.AboutContentRequest;
import com.prem.portfolio.portfolio.dto.response.AboutContentResponse;
import com.prem.portfolio.portfolio.entity.AboutContent;
import org.springframework.stereotype.Component;

@Component
public class AboutContentMapper {

    public AboutContent toEntity(AboutContentRequest request) {

        AboutContent aboutContent = new AboutContent();

        aboutContent.setProfileId(request.getProfileId());
        aboutContent.setTitle(request.getTitle());
        aboutContent.setDescription(request.getDescription());
        aboutContent.setDisplayOrder(
                request.getDisplayOrder() != null ? request.getDisplayOrder() : 0
        );
        aboutContent.setActive(
                request.getActive() != null ? request.getActive() : true
        );

        return aboutContent;
    }

    public void updateEntity(
            AboutContent aboutContent,
            AboutContentRequest request
    ) {

        aboutContent.setProfileId(request.getProfileId());
        aboutContent.setTitle(request.getTitle());
        aboutContent.setDescription(request.getDescription());
        aboutContent.setDisplayOrder(
                request.getDisplayOrder() != null ? request.getDisplayOrder() : 0
        );

        if (request.getActive() != null) {
            aboutContent.setActive(request.getActive());
        }
    }

    public AboutContentResponse toResponse(AboutContent aboutContent) {

        AboutContentResponse response = new AboutContentResponse();

        response.setAboutId(aboutContent.getAboutId());
        response.setProfileId(aboutContent.getProfileId());
        response.setTitle(aboutContent.getTitle());
        response.setDescription(aboutContent.getDescription());
        response.setDisplayOrder(aboutContent.getDisplayOrder());
        response.setActive(aboutContent.getActive());
        response.setCreatedAt(aboutContent.getCreatedAt());
        response.setUpdatedAt(aboutContent.getUpdatedAt());
        response.setCreatedBy(aboutContent.getCreatedBy());
        response.setUpdatedBy(aboutContent.getUpdatedBy());

        return response;
    }
}

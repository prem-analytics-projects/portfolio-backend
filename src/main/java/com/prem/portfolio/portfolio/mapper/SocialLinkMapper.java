
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.SocialLinkRequest;
import com.prem.portfolio.portfolio.dto.response.SocialLinkResponse;
import com.prem.portfolio.portfolio.entity.SocialLink;

@Component
public class SocialLinkMapper {

    public SocialLink toEntity(SocialLinkRequest request) {
        SocialLink socialLink = new SocialLink();
        updateEntity(socialLink, request);
        return socialLink;
    }

    public void updateEntity(
            SocialLink socialLink,
            SocialLinkRequest request) {

        socialLink.setProfileId(request.getProfileId());
        socialLink.setPlatformName(request.getPlatformName());
        socialLink.setLinkUrl(request.getLinkUrl());
        socialLink.setIconName(request.getIconName());

        socialLink.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );

        socialLink.setActive(
                request.getActive() != null
                        ? request.getActive()
                        : true
        );
    }

    public SocialLinkResponse toResponse(SocialLink socialLink) {

        SocialLinkResponse response = new SocialLinkResponse();

        response.setSocialLinkId(socialLink.getSocialLinkId());
        response.setProfileId(socialLink.getProfileId());
        response.setPlatformName(socialLink.getPlatformName());
        response.setLinkUrl(socialLink.getLinkUrl());
        response.setIconName(socialLink.getIconName());
        response.setDisplayOrder(socialLink.getDisplayOrder());
        response.setActive(socialLink.getActive());
        response.setCreatedAt(socialLink.getCreatedAt());
        response.setUpdatedAt(socialLink.getUpdatedAt());
        response.setCreatedBy(socialLink.getCreatedBy());
        response.setUpdatedBy(socialLink.getUpdatedBy());

        return response;
    }
}

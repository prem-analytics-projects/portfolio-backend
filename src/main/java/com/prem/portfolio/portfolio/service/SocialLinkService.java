
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.SocialLinkRequest;
import com.prem.portfolio.portfolio.dto.response.SocialLinkResponse;

import java.util.List;

public interface SocialLinkService {

    SocialLinkResponse createSocialLink(SocialLinkRequest request);

    SocialLinkResponse updateSocialLink(
            Long socialLinkId,
            SocialLinkRequest request
    );

    SocialLinkResponse getSocialLinkById(Long socialLinkId);

    List<SocialLinkResponse> getSocialLinks(
            Long profileId,
            Boolean active
    );

    void deleteSocialLink(Long socialLinkId);
}

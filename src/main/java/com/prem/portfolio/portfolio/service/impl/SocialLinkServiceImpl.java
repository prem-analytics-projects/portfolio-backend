
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.portfolio.dto.request.SocialLinkRequest;
import com.prem.portfolio.portfolio.dto.response.SocialLinkResponse;
import com.prem.portfolio.portfolio.entity.SocialLink;
import com.prem.portfolio.portfolio.mapper.SocialLinkMapper;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.repository.SocialLinkRepository;
import com.prem.portfolio.portfolio.service.SocialLinkService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SocialLinkServiceImpl implements SocialLinkService {

    private final SocialLinkRepository socialLinkRepository;
    private final ProfileRepository profileRepository;
    private final SocialLinkMapper socialLinkMapper;

    public SocialLinkServiceImpl(
            SocialLinkRepository socialLinkRepository,
            ProfileRepository profileRepository,
            SocialLinkMapper socialLinkMapper) {

        this.socialLinkRepository = socialLinkRepository;
        this.profileRepository = profileRepository;
        this.socialLinkMapper = socialLinkMapper;
    }

    @Override
    public SocialLinkResponse createSocialLink(SocialLinkRequest request) {

        validateProfile(request.getProfileId());

        SocialLink socialLink = socialLinkMapper.toEntity(request);

        SocialLink savedSocialLink =
                socialLinkRepository.save(socialLink);

        return socialLinkMapper.toResponse(savedSocialLink);
    }

    @Override
    public SocialLinkResponse updateSocialLink(
            Long socialLinkId,
            SocialLinkRequest request) {

        SocialLink socialLink = getActiveSocialLink(socialLinkId);

        validateProfile(request.getProfileId());

        socialLinkMapper.updateEntity(socialLink, request);

        SocialLink updatedSocialLink =
                socialLinkRepository.save(socialLink);

        return socialLinkMapper.toResponse(updatedSocialLink);
    }

    @Override
    @Transactional(readOnly = true)
    public SocialLinkResponse getSocialLinkById(Long socialLinkId) {

        return socialLinkMapper.toResponse(
                getActiveSocialLink(socialLinkId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialLinkResponse> getSocialLinks(
            Long profileId,
            Boolean active) {

        validateProfile(profileId);

        return socialLinkRepository
                .findSocialLinks(profileId, active)
                .stream()
                .map(socialLinkMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteSocialLink(Long socialLinkId) {

        SocialLink socialLink = getActiveSocialLink(socialLinkId);

        socialLink.setActive(false);

        socialLinkRepository.save(socialLink);
    }

    private SocialLink getActiveSocialLink(Long socialLinkId) {

        return socialLinkRepository
                .findBySocialLinkIdAndActiveTrue(socialLinkId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active social link not found with ID: "
                                        + socialLinkId
                        ));
    }

    private void validateProfile(Long profileId) {

        profileRepository.findById(profileId)
                .filter(profile ->
                        Boolean.TRUE.equals(profile.getActive()))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active profile not found with ID: "
                                        + profileId
                        ));
    }
}

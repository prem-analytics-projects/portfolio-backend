
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.common.exception.ResourceNotFoundException;
import com.prem.portfolio.portfolio.dto.request.AboutContentRequest;
import com.prem.portfolio.portfolio.dto.response.AboutContentResponse;
import com.prem.portfolio.portfolio.entity.AboutContent;
import com.prem.portfolio.portfolio.entity.Profile;
import com.prem.portfolio.portfolio.mapper.AboutContentMapper;
import com.prem.portfolio.portfolio.repository.AboutContentRepository;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.AboutContentService;

@Service
@Transactional
public class AboutContentServiceImpl implements AboutContentService {

    private final AboutContentRepository aboutContentRepository;
    private final ProfileRepository profileRepository;
    private final AboutContentMapper aboutContentMapper;

    public AboutContentServiceImpl(
            AboutContentRepository aboutContentRepository,
            ProfileRepository profileRepository,
            AboutContentMapper aboutContentMapper) {

        this.aboutContentRepository = aboutContentRepository;
        this.profileRepository = profileRepository;
        this.aboutContentMapper = aboutContentMapper;
    }

    // CREATE ABOUT CONTENT
    @Override
    public AboutContentResponse createAboutContent(
            AboutContentRequest request) {

        validateProfile(request.getProfileId());

        AboutContent aboutContent =
                aboutContentMapper.toEntity(request);

        AboutContent savedContent =
                aboutContentRepository.save(aboutContent);

        return aboutContentMapper.toResponse(savedContent);
    }

    // UPDATE ABOUT CONTENT
    @Override
    public AboutContentResponse updateAboutContent(
            Long aboutId,
            AboutContentRequest request) {

        AboutContent aboutContent =
                findAboutContentOrThrow(aboutId);

        validateProfile(request.getProfileId());

        aboutContentMapper.updateEntity(aboutContent, request);

        AboutContent updatedContent =
                aboutContentRepository.save(aboutContent);

        return aboutContentMapper.toResponse(updatedContent);
    }

    // GET ABOUT CONTENT BY ID
    @Override
    @Transactional(readOnly = true)
    public AboutContentResponse getAboutContentById(Long aboutId) {

        AboutContent aboutContent =
                findAboutContentOrThrow(aboutId);

        return aboutContentMapper.toResponse(aboutContent);
    }

    // GET ALL ABOUT CONTENT
    @Override
    @Transactional(readOnly = true)
    public List<AboutContentResponse> getAllAboutContent(
            Long profileId,
            Boolean active) {

        if (profileId != null) {
            findProfileOrThrow(profileId);
        }

        List<AboutContent> contents =
                aboutContentRepository.findAboutContent(profileId, active);

        return contents.stream()
                .map(aboutContentMapper::toResponse)
                .toList();
    }

    // SOFT DELETE ABOUT CONTENT
    @Override
    public void deleteAboutContent(Long aboutId) {

        AboutContent aboutContent =
                findAboutContentOrThrow(aboutId);

        aboutContent.setActive(false);

        aboutContentRepository.save(aboutContent);
    }

    // VALIDATE PROFILE
    private void validateProfile(Long profileId) {

        Profile profile = findProfileOrThrow(profileId);

        if (!Boolean.TRUE.equals(profile.getActive())) {
            throw new IllegalArgumentException(
                    "Cannot create or update About Content for an inactive profile."
            );
        }
    }

    // FIND PROFILE OR THROW EXCEPTION
    private Profile findProfileOrThrow(Long profileId) {

        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile not found with ID: " + profileId
                ));
    }

    // FIND ABOUT CONTENT OR THROW EXCEPTION
    private AboutContent findAboutContentOrThrow(Long aboutId) {

        return aboutContentRepository.findById(aboutId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "About Content not found with ID: " + aboutId
                ));
    }
}

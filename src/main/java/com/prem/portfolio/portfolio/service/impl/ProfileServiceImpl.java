
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.common.exception.ResourceNotFoundException;
import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.request.ProfileRequest;
import com.prem.portfolio.portfolio.dto.response.ProfileResponse;
import com.prem.portfolio.portfolio.entity.Profile;
import com.prem.portfolio.portfolio.mapper.ProfileMapper;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.ProfileService;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final MediaRepository mediaRepository;

    public ProfileServiceImpl(
            ProfileRepository profileRepository,
            ProfileMapper profileMapper,
            MediaRepository mediaRepository) {

        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.mediaRepository = mediaRepository;
    }

    // CREATE PROFILE
    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

        // Validate profile image
        validateMedia(
                request.getProfileImageId(),
                List.of("IMAGE"),
                "Profile image"
        );

        // Validate resume
        validateMedia(
                request.getResumeMediaId(),
                List.of("PDF", "DOCUMENT"),
                "Resume"
        );

        Profile profile = profileMapper.toEntity(request);

        Profile savedProfile = profileRepository.save(profile);

        return profileMapper.toResponse(savedProfile);
    }

    // GET ALL PROFILES
    @Override
    @Transactional(readOnly = true)
    public List<ProfileResponse> getAllProfiles() {

        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toResponse)
                .toList();
    }

    // GET PROFILE BY ID
    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfileById(Long profileId) {

        Profile profile = findProfileOrThrow(profileId);

        return profileMapper.toResponse(profile);
    }

    // UPDATE PROFILE
    @Override
    public ProfileResponse updateProfile(
            Long profileId,
            ProfileRequest request) {

        Profile profile = findProfileOrThrow(profileId);

        // Validate profile image
        validateMedia(
                request.getProfileImageId(),
                List.of("IMAGE"),
                "Profile image"
        );

        // Validate resume
        validateMedia(
                request.getResumeMediaId(),
                List.of("PDF", "DOCUMENT"),
                "Resume"
        );

        profileMapper.updateEntity(profile, request);

        Profile updatedProfile = profileRepository.save(profile);

        return profileMapper.toResponse(updatedProfile);
    }

    // SOFT DELETE PROFILE
    @Override
    public void deleteProfile(Long profileId) {

        Profile profile = findProfileOrThrow(profileId);

        profile.setActive(false);

        profileRepository.save(profile);
    }

    // VALIDATE MEDIA
    private void validateMedia(
            Long mediaId,
            List<String> allowedTypes,
            String fieldName) {

        // Media is optional
        if (mediaId == null) {
            return;
        }

        // Check whether media exists
        MediaAsset media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        fieldName + " not found with ID: " + mediaId
                ));

        // Check whether media is active
        if (!Boolean.TRUE.equals(media.getIsActive())) {
            throw new IllegalArgumentException(
                    fieldName + " is inactive."
            );
        }

        // Check whether media type is valid
        if (!allowedTypes.contains(media.getFileType())) {
            throw new IllegalArgumentException(
                    fieldName + " must be one of these types: "
                            + allowedTypes
            );
        }
    }

    // COMMON PROFILE LOOKUP
    private Profile findProfileOrThrow(Long profileId) {

        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile not found with ID: " + profileId
                ));
    }
}

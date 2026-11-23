
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

        ProfileResponse response =
                profileMapper.toResponse(savedProfile);

        populateMediaUrls(response);

        return response;
    }

    // GET ALL PROFILES WITH SEARCH, FILTERING AND PAGINATION
    @Override
    @Transactional(readOnly = true)
    public Page<ProfileResponse> getAllProfiles(
            String search,
            Boolean active,
            Pageable pageable) {

        Page<Profile> profiles = profileRepository.searchProfiles(
                search,
                active,
                pageable
        );

        return profiles.map(profile -> {

            ProfileResponse response =
                    profileMapper.toResponse(profile);

            populateMediaUrls(response);

            return response;
        });
    }

    // GET PROFILE BY ID
    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfileById(Long profileId) {

        Profile profile = findProfileOrThrow(profileId);

        ProfileResponse response =
                profileMapper.toResponse(profile);

        populateMediaUrls(response);

        return response;
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

        ProfileResponse response =
                profileMapper.toResponse(updatedProfile);

        populateMediaUrls(response);

        return response;
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

    // POPULATE PROFILE MEDIA URLS
    private void populateMediaUrls(ProfileResponse response) {

        response.setProfileImageUrl(
                buildMediaUrl(response.getProfileImageId())
        );

        response.setResumeUrl(
                buildMediaUrl(response.getResumeMediaId())
        );
    }

    // BUILD MEDIA FILE URL
    private String buildMediaUrl(Long mediaId) {

        // Media is optional
        if (mediaId == null) {
            return null;
        }

        return mediaRepository.findById(mediaId)
                .filter(media -> Boolean.TRUE.equals(media.getIsActive()))
                .map(MediaAsset::getFileName)
                .map(fileName ->
                        ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .pathSegment(
                                        "api",
                                        "v1",
                                        "media",
                                        "files",
                                        fileName
                                )
                                .toUriString()
                )
                .orElse(null);
    }

    // COMMON PROFILE LOOKUP
    private Profile findProfileOrThrow(Long profileId) {

        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile not found with ID: " + profileId
                ));
    }
}

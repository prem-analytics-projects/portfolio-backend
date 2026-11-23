
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.common.exception.ResourceNotFoundException;
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

    public ProfileServiceImpl(
            ProfileRepository profileRepository,
            ProfileMapper profileMapper) {

        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    // CREATE PROFILE
    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

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

    // COMMON PROFILE LOOKUP
    private Profile findProfileOrThrow(Long profileId) {

        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profile not found with ID: " + profileId));
    }
}

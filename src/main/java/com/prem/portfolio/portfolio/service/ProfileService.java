
package com.prem.portfolio.portfolio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prem.portfolio.portfolio.dto.request.ProfileRequest;
import com.prem.portfolio.portfolio.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);

    Page<ProfileResponse> getAllProfiles(
            String search,
            Boolean active,
            Pageable pageable
    );

    ProfileResponse getProfileById(Long profileId);

    ProfileResponse updateProfile(
            Long profileId,
            ProfileRequest request
    );

    void deleteProfile(Long profileId);
}

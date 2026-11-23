
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.ProfileRequest;
import com.prem.portfolio.portfolio.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);

    List<ProfileResponse> getAllProfiles();

    ProfileResponse getProfileById(Long profileId);

    ProfileResponse updateProfile(Long profileId, ProfileRequest request);

    void deleteProfile(Long profileId);
}

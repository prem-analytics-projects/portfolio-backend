
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.ProfileRequest;
import com.prem.portfolio.portfolio.dto.response.ProfileResponse;
import com.prem.portfolio.portfolio.entity.Profile;

@Component
public class ProfileMapper {

    // Convert request DTO to entity
    public Profile toEntity(ProfileRequest request) {

        Profile profile = new Profile();

        profile.setFullName(request.getFullName());
        profile.setProfessionalTitle(request.getProfessionalTitle());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());
        profile.setShortDescription(request.getShortDescription());
        profile.setProfileImageId(request.getProfileImageId());
        profile.setResumeMediaId(request.getResumeMediaId());
        profile.setActive(request.getActive());

        return profile;
    }

    // Update existing entity using request DTO
    public void updateEntity(Profile profile, ProfileRequest request) {

        profile.setFullName(request.getFullName());
        profile.setProfessionalTitle(request.getProfessionalTitle());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());
        profile.setShortDescription(request.getShortDescription());
        profile.setProfileImageId(request.getProfileImageId());
        profile.setResumeMediaId(request.getResumeMediaId());
        profile.setActive(request.getActive());
    }

    // Convert entity to response DTO
    public ProfileResponse toResponse(Profile profile) {

        ProfileResponse response = new ProfileResponse();

        response.setProfileId(profile.getProfileId());
        response.setFullName(profile.getFullName());
        response.setProfessionalTitle(profile.getProfessionalTitle());
        response.setEmail(profile.getEmail());
        response.setPhone(profile.getPhone());
        response.setLocation(profile.getLocation());
        response.setShortDescription(profile.getShortDescription());
        response.setProfileImageId(profile.getProfileImageId());
        response.setResumeMediaId(profile.getResumeMediaId());
        response.setActive(profile.getActive());
        response.setCreatedAt(profile.getCreatedAt());
        response.setUpdatedAt(profile.getUpdatedAt());
        response.setCreatedBy(profile.getCreatedBy());
        response.setUpdatedBy(profile.getUpdatedBy());

        return response;
    }
}


package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.portfolio.dto.request.ExperienceRequest;
import com.prem.portfolio.portfolio.dto.response.ExperienceResponse;
import com.prem.portfolio.portfolio.entity.Company;
import com.prem.portfolio.portfolio.entity.Experience;
import com.prem.portfolio.portfolio.mapper.ExperienceMapper;
import com.prem.portfolio.portfolio.repository.CompanyRepository;
import com.prem.portfolio.portfolio.repository.ExperienceRepository;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.ExperienceService;
import com.prem.portfolio.media.repository.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final ExperienceMapper experienceMapper;
    private final MediaRepository mediaRepository;
    public ExperienceServiceImpl(
        ExperienceRepository experienceRepository,
        ProfileRepository profileRepository,
        CompanyRepository companyRepository,
        ExperienceMapper experienceMapper,
        MediaRepository mediaRepository) {

    this.experienceRepository = experienceRepository;
    this.profileRepository = profileRepository;
    this.companyRepository = companyRepository;
    this.experienceMapper = experienceMapper;
    this.mediaRepository = mediaRepository;
}

    @Override
    public ExperienceResponse createExperience(ExperienceRequest request) {

        validateProfile(request.getProfileId());
        validateCompany(request.getCompanyId());
        validateDates(request);

        if (Boolean.TRUE.equals(request.getCurrent())
                && Boolean.TRUE.equals(request.getActive())
                && experienceRepository.existsByProfileIdAndCurrentTrueAndActiveTrue(
                        request.getProfileId())) {

            throw new IllegalArgumentException(
                    "This profile already has an active current experience."
            );
        }

        Experience experience = experienceMapper.toEntity(request);

        Experience savedExperience = experienceRepository.save(experience);

        return buildResponse(savedExperience);
    }

    @Override
    public ExperienceResponse updateExperience(
            Long experienceId,
            ExperienceRequest request) {

        Experience experience = getActiveExperience(experienceId);

        validateProfile(request.getProfileId());
        validateCompany(request.getCompanyId());
        validateDates(request);

        if (Boolean.TRUE.equals(request.getCurrent())
                && Boolean.TRUE.equals(request.getActive())) {

            boolean anotherCurrentExists =
                    experienceRepository
                            .existsByProfileIdAndCurrentTrueAndActiveTrue(
                                    request.getProfileId());

            if (anotherCurrentExists) {
                boolean isSameExperience =
                        Boolean.TRUE.equals(experience.getCurrent())
                                && experience.getProfileId()
                                        .equals(request.getProfileId());

                if (!isSameExperience) {
                    throw new IllegalArgumentException(
                            "This profile already has an active current experience."
                    );
                }
            }
        }

        experienceMapper.updateEntity(experience, request);

        Experience updatedExperience =
                experienceRepository.save(experience);

        return buildResponse(updatedExperience);
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceById(Long experienceId) {

        return buildResponse(getActiveExperience(experienceId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getExperiences(
            Long profileId,
            Boolean active) {

        validateProfile(profileId);

        return experienceRepository.findExperiences(profileId, active)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void deleteExperience(Long experienceId) {

        Experience experience = getActiveExperience(experienceId);

        experience.setActive(false);

        experienceRepository.save(experience);
    }

    private Experience getActiveExperience(Long experienceId) {

        return experienceRepository
                .findByExperienceIdAndActiveTrue(experienceId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active experience not found with ID: "
                                        + experienceId
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

    private void validateCompany(Long companyId) {

        if (companyId == null) {
            return;
        }

        companyRepository.findByCompanyIdAndActiveTrue(companyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active company not found with ID: "
                                        + companyId
                        ));
    }

    private void validateDates(ExperienceRequest request) {

        if (Boolean.TRUE.equals(request.getCurrent())
                && request.getEndDate() != null) {

            throw new IllegalArgumentException(
                    "Current experience must not have an end date."
            );
        }

        if (!Boolean.TRUE.equals(request.getCurrent())
                && request.getStartDate() != null
                && request.getEndDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {

            throw new IllegalArgumentException(
                    "End date cannot be before start date."
            );
        }
    }

    private ExperienceResponse buildResponse(Experience experience) {

    ExperienceResponse response =
            experienceMapper.toResponse(experience);

    if (experience.getCompanyId() != null) {

        Company company = companyRepository
                .findByCompanyIdAndActiveTrue(experience.getCompanyId())
                .orElse(null);

        if (company != null) {

            response.setCompanyName(company.getCompanyName());
            response.setCompanyWebsite(company.getWebsite());
            response.setCompanyLocation(company.getLocation());

            if (company.getLogoMediaId() != null) {

                mediaRepository.findById(company.getLogoMediaId())
                        .filter(media ->
                                Boolean.TRUE.equals(media.getIsActive()))
                        .ifPresent(media ->
                                response.setCompanyLogoUrl(
                                        media.getFileUrl()
                                ));
            }
        }
    }

    return response;
}
}

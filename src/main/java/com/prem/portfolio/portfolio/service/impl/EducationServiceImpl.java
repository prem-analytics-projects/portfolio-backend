
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.request.EducationRequest;
import com.prem.portfolio.portfolio.dto.response.EducationResponse;
import com.prem.portfolio.portfolio.entity.Education;
import com.prem.portfolio.portfolio.mapper.EducationMapper;
import com.prem.portfolio.portfolio.repository.EducationRepository;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.EducationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final ProfileRepository profileRepository;
    private final MediaRepository mediaRepository;
    private final EducationMapper educationMapper;

    public EducationServiceImpl(
            EducationRepository educationRepository,
            ProfileRepository profileRepository,
            MediaRepository mediaRepository,
            EducationMapper educationMapper) {

        this.educationRepository = educationRepository;
        this.profileRepository = profileRepository;
        this.mediaRepository = mediaRepository;
        this.educationMapper = educationMapper;
    }

    @Override
    public EducationResponse createEducation(EducationRequest request) {

        validateProfile(request.getProfileId());
        validateDates(request);
        validateCertificate(request.getCertificateMediaId());

        Education education = educationMapper.toEntity(request);

        Education savedEducation = educationRepository.save(education);

        return toResponse(savedEducation);
    }

    @Override
    public EducationResponse updateEducation(
            Long educationId,
            EducationRequest request) {

        Education education = getActiveEducation(educationId);

        validateProfile(request.getProfileId());
        validateDates(request);
        validateCertificate(request.getCertificateMediaId());

        educationMapper.updateEntity(education, request);

        Education updatedEducation = educationRepository.save(education);

        return toResponse(updatedEducation);
    }

    @Override
    @Transactional(readOnly = true)
    public EducationResponse getEducationById(Long educationId) {

        Education education = getActiveEducation(educationId);

        return toResponse(education);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getEducations(
            Long profileId,
            Boolean active) {

        validateProfile(profileId);

        return educationRepository.findEducations(profileId, active)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void deleteEducation(Long educationId) {

        Education education = getActiveEducation(educationId);

        education.setActive(false);

        educationRepository.save(education);
    }

    private Education getActiveEducation(Long educationId) {

        return educationRepository
                .findByEducationIdAndActiveTrue(educationId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Education not found with ID: " + educationId
                        )
                );
    }

    private void validateProfile(Long profileId) {

        boolean profileExists = profileRepository
                .findById(profileId)
                .filter(profile ->
                        Boolean.TRUE.equals(profile.getActive())
                )
                .isPresent();

        if (!profileExists) {
            throw new IllegalArgumentException(
                    "Active profile not found with ID: " + profileId
            );
        }
    }

    private void validateDates(EducationRequest request) {

        if (request.getStartDate() != null
                && request.getEndDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {

            throw new IllegalArgumentException(
                    "End date cannot be earlier than start date."
            );
        }
    }

    private void validateCertificate(Long certificateMediaId) {

        if (certificateMediaId == null) {
            return;
        }

        MediaAsset media = mediaRepository.findById(certificateMediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Certificate media not found with ID: "
                                        + certificateMediaId
                        )
                );

        if (!Boolean.TRUE.equals(media.getIsActive())) {
            throw new IllegalArgumentException(
                    "Certificate media is inactive."
            );
        }

        if (!"PDF".equalsIgnoreCase(media.getFileType())) {
            throw new IllegalArgumentException(
                    "Education certificate must be a PDF file."
            );
        }
    }

    private EducationResponse toResponse(Education education) {

        EducationResponse response = educationMapper.toResponse(education);

        if (education.getCertificateMediaId() != null) {

            mediaRepository.findById(education.getCertificateMediaId())
                    .filter(media ->
                            Boolean.TRUE.equals(media.getIsActive())
                    )
                    .ifPresent(media ->
                            response.setCertificateUrl(media.getFileUrl())
                    );
        }

        return response;
    }
}

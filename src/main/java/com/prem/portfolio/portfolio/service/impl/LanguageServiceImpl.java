
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.portfolio.dto.request.LanguageRequest;
import com.prem.portfolio.portfolio.dto.response.LanguageResponse;
import com.prem.portfolio.portfolio.entity.Language;
import com.prem.portfolio.portfolio.mapper.LanguageMapper;
import com.prem.portfolio.portfolio.repository.LanguageRepository;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.LanguageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ProfileRepository profileRepository;
    private final LanguageMapper languageMapper;

    public LanguageServiceImpl(
            LanguageRepository languageRepository,
            ProfileRepository profileRepository,
            LanguageMapper languageMapper) {

        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.languageMapper = languageMapper;
    }

    @Override
    public LanguageResponse createLanguage(LanguageRequest request) {

        validateProfile(request.getProfileId());

        validateDuplicateLanguage(
                request.getProfileId(),
                request.getLanguageName(),
                null,
                request.getActive()
        );

        Language language = languageMapper.toEntity(request);

        Language savedLanguage = languageRepository.save(language);

        return languageMapper.toResponse(savedLanguage);
    }

    @Override
    public LanguageResponse updateLanguage(
            Long languageId,
            LanguageRequest request) {

        Language language = getActiveLanguage(languageId);

        validateProfile(request.getProfileId());

        validateDuplicateLanguage(
                request.getProfileId(),
                request.getLanguageName(),
                languageId,
                request.getActive()
        );

        languageMapper.updateEntity(language, request);

        Language updatedLanguage = languageRepository.save(language);

        return languageMapper.toResponse(updatedLanguage);
    }

    @Override
    @Transactional(readOnly = true)
    public LanguageResponse getLanguageById(Long languageId) {

        return languageMapper.toResponse(
                getActiveLanguage(languageId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<LanguageResponse> getLanguages(
            Long profileId,
            Boolean active) {

        validateProfile(profileId);

        return languageRepository.findLanguages(profileId, active)
                .stream()
                .map(languageMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteLanguage(Long languageId) {

        Language language = getActiveLanguage(languageId);

        language.setActive(false);

        languageRepository.save(language);
    }

    private Language getActiveLanguage(Long languageId) {

        return languageRepository
                .findByLanguageIdAndActiveTrue(languageId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active language not found with ID: "
                                        + languageId
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

    private void validateDuplicateLanguage(
            Long profileId,
            String languageName,
            Long currentLanguageId,
            Boolean active) {

        if (!Boolean.TRUE.equals(active)) {
            return;
        }

        boolean duplicateExists =
                languageRepository
                        .existsByProfileIdAndLanguageNameIgnoreCaseAndActiveTrue(
                                profileId,
                                languageName.trim()
                        );

        if (!duplicateExists) {
            return;
        }

        if (currentLanguageId != null) {
            Language existingLanguage = languageRepository.findAll()
                    .stream()
                    .filter(item ->
                            Boolean.TRUE.equals(item.getActive())
                                    && item.getProfileId().equals(profileId)
                                    && item.getLanguageName()
                                            .equalsIgnoreCase(languageName.trim()))
                    .findFirst()
                    .orElse(null);

            if (existingLanguage != null
                    && existingLanguage.getLanguageId()
                            .equals(currentLanguageId)) {
                return;
            }
        }

        throw new IllegalArgumentException(
                "This language already exists for the selected profile."
        );
    }
}

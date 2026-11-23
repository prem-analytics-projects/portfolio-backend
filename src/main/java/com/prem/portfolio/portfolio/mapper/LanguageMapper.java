
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.LanguageRequest;
import com.prem.portfolio.portfolio.dto.response.LanguageResponse;
import com.prem.portfolio.portfolio.entity.Language;

@Component
public class LanguageMapper {

    public Language toEntity(LanguageRequest request) {
        Language language = new Language();
        updateEntity(language, request);
        return language;
    }

    public void updateEntity(Language language, LanguageRequest request) {

        language.setProfileId(request.getProfileId());
        language.setLanguageName(request.getLanguageName());
        language.setProficiencyLevel(request.getProficiencyLevel());

        language.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );

        language.setActive(
                request.getActive() != null
                        ? request.getActive()
                        : true
        );
    }

    public LanguageResponse toResponse(Language language) {

        LanguageResponse response = new LanguageResponse();

        response.setLanguageId(language.getLanguageId());
        response.setProfileId(language.getProfileId());
        response.setLanguageName(language.getLanguageName());
        response.setProficiencyLevel(language.getProficiencyLevel());

        response.setDisplayOrder(language.getDisplayOrder());
        response.setActive(language.getActive());
        response.setCreatedAt(language.getCreatedAt());
        response.setUpdatedAt(language.getUpdatedAt());
        response.setCreatedBy(language.getCreatedBy());
        response.setUpdatedBy(language.getUpdatedBy());

        response.setProficiencyLabel(
                getProficiencyLabel(language.getProficiencyLevel())
        );

        return response;
    }

    private String getProficiencyLabel(Short level) {

        if (level == null) {
            return "Not specified";
        }

        return switch (level) {
            case 1 -> "Beginner";
            case 2 -> "Elementary";
            case 3 -> "Intermediate";
            case 4 -> "Advanced";
            case 5 -> "Native / Fluent";
            default -> "Not specified";
        };
    }
}

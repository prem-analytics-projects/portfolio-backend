
package com.prem.portfolio.portfolio.dto.response;

public class PublicLanguageResponse {

    private Long languageId;
    private Long profileId;
    private String languageName;
    private Short proficiencyLevel;
    private String proficiencyLabel;
    private Integer displayOrder;

    public PublicLanguageResponse() {
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Short getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(Short proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public String getProficiencyLabel() {
        return proficiencyLabel;
    }

    public void setProficiencyLabel(String proficiencyLabel) {
        this.proficiencyLabel = proficiencyLabel;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}


package com.prem.portfolio.portfolio.dto.response;

import java.math.BigDecimal;

public class PublicSkillResponse {

    private Long skillId;
    private Long profileId;
    private Long skillCategoryId;
    private String skillName;
    private Short proficiencyLevel;
    private BigDecimal yearsExperience;
    private Integer displayOrder;

    public PublicSkillResponse() {
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getSkillCategoryId() {
        return skillCategoryId;
    }

    public void setSkillCategoryId(Long skillCategoryId) {
        this.skillCategoryId = skillCategoryId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Short getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(Short proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public BigDecimal getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(BigDecimal yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}

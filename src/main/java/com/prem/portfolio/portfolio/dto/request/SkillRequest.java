
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class SkillRequest {

    @NotNull(message = "Profile ID is required")
    @Positive(message = "Profile ID must be positive")
    private Long profileId;

    private Long skillCategoryId;

    @NotBlank(message = "Skill name is required")
    private String skillName;

    @NotNull(message = "Proficiency level is required")
    @Min(value = 1, message = "Proficiency level must be between 1 and 5")
    @Max(value = 5, message = "Proficiency level must be between 1 and 5")
    private Short proficiencyLevel;

    @DecimalMin(value = "0.0", message = "Years of experience cannot be negative")
    private BigDecimal yearsExperience = BigDecimal.ZERO;

    private Integer displayOrder = 0;

    private Boolean active = true;

    public SkillRequest() {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

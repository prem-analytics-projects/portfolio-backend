
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class ProjectRequest {

    @NotNull(message = "Profile ID is required")
    @Positive(message = "Profile ID must be positive")
    private Long profileId;

    @NotBlank(message = "Project title is required")
    @Size(max = 255, message = "Project title cannot exceed 255 characters")
    private String title;

    private String description;

    private String problemStatement;

    private String solutionSummary;

    private String impactSummary;

    private LocalDate startDate;

    private LocalDate endDate;

    @Pattern(
        regexp = "^$|^https?://.+$",
        message = "Project URL must start with http:// or https://"
    )
    private String projectUrl;

    @Pattern(
        regexp = "^$|^https?://.+$",
        message = "GitHub URL must start with http:// or https://"
    )
    private String githubUrl;

    @Positive(message = "Thumbnail media ID must be positive")
    private Long thumbnailMediaId;

    @NotNull(message = "Featured status is required")
    private Boolean featured = false;

    @NotNull(message = "Active status is required")
    private Boolean active = true;

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order cannot be negative")
    private Integer displayOrder = 0;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getSolutionSummary() {
        return solutionSummary;
    }

    public void setSolutionSummary(String solutionSummary) {
        this.solutionSummary = solutionSummary;
    }

    public String getImpactSummary() {
        return impactSummary;
    }

    public void setImpactSummary(String impactSummary) {
        this.impactSummary = impactSummary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public Long getThumbnailMediaId() {
        return thumbnailMediaId;
    }

    public void setThumbnailMediaId(Long thumbnailMediaId) {
        this.thumbnailMediaId = thumbnailMediaId;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}

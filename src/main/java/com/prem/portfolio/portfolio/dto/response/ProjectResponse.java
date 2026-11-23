
package com.prem.portfolio.portfolio.dto.response;
import java.util.List;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ProjectResponse {

    private Long projectId;
    private Long profileId;
    private String title;
    private String description;

    private String problemStatement;
    private String solutionSummary;
    private String impactSummary;

    private LocalDate startDate;
    private LocalDate endDate;

    private String projectUrl;
    private String githubUrl;

    private Long thumbnailMediaId;
    private String thumbnailUrl;

    private Boolean featured;
    private Boolean active;
    private Integer displayOrder;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Long createdBy;
    private Long updatedBy;
    private List<ProjectImpactMetricResponse> impactMetrics;
    private List<ProjectMediaResponse> projectMedia;
    private List<ProjectTechnologyResponse> technologies;
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public List<ProjectImpactMetricResponse> getImpactMetrics() {
    return impactMetrics;
}

public void setImpactMetrics(
        List<ProjectImpactMetricResponse> impactMetrics
) {
    this.impactMetrics = impactMetrics;
}
public List<ProjectMediaResponse> getProjectMedia() {
    return projectMedia;
}

public void setProjectMedia(
        List<ProjectMediaResponse> projectMedia
) {
    this.projectMedia = projectMedia;
}
public List<ProjectTechnologyResponse> getTechnologies() {
    return technologies;
}

public void setTechnologies(List<ProjectTechnologyResponse> technologies) {
    this.technologies = technologies;
}
}

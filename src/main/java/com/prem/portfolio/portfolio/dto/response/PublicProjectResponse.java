
package com.prem.portfolio.portfolio.dto.response;

import java.time.LocalDate;
import java.util.List;

public class PublicProjectResponse {

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
    private String thumbnailUrl;
    private Boolean featured;
    private Integer displayOrder;

    private List<PublicProjectMetric> impactMetrics;
    private List<PublicProjectMedia> projectMedia;
    private List<PublicProjectTechnology> technologies;

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProblemStatement() { return problemStatement; }
    public void setProblemStatement(String problemStatement) { this.problemStatement = problemStatement; }

    public String getSolutionSummary() { return solutionSummary; }
    public void setSolutionSummary(String solutionSummary) { this.solutionSummary = solutionSummary; }

    public String getImpactSummary() { return impactSummary; }
    public void setImpactSummary(String impactSummary) { this.impactSummary = impactSummary; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getProjectUrl() { return projectUrl; }
    public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    public List<PublicProjectMetric> getImpactMetrics() { return impactMetrics; }
    public void setImpactMetrics(List<PublicProjectMetric> impactMetrics) { this.impactMetrics = impactMetrics; }

    public List<PublicProjectMedia> getProjectMedia() { return projectMedia; }
    public void setProjectMedia(List<PublicProjectMedia> projectMedia) { this.projectMedia = projectMedia; }

    public List<PublicProjectTechnology> getTechnologies() { return technologies; }
    public void setTechnologies(List<PublicProjectTechnology> technologies) { this.technologies = technologies; }

    public static class PublicProjectMetric {
        private Long impactMetricId;
        private String metricLabel;
        private String metricValue;
        private String metricUnit;
        private String description;
        private Integer displayOrder;

        public Long getImpactMetricId() { return impactMetricId; }
        public void setImpactMetricId(Long impactMetricId) { this.impactMetricId = impactMetricId; }

        public String getMetricLabel() { return metricLabel; }
        public void setMetricLabel(String metricLabel) { this.metricLabel = metricLabel; }

        public String getMetricValue() { return metricValue; }
        public void setMetricValue(String metricValue) { this.metricValue = metricValue; }

        public String getMetricUnit() { return metricUnit; }
        public void setMetricUnit(String metricUnit) { this.metricUnit = metricUnit; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Integer getDisplayOrder() { return displayOrder; }
        public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    }

    public static class PublicProjectMedia {
        private Long projectMediaId;
        private String fileName;
        private String fileType;
        private String mimeType;
        private String mediaUrl;
        private String caption;
        private Integer displayOrder;

        public Long getProjectMediaId() { return projectMediaId; }
        public void setProjectMediaId(Long projectMediaId) { this.projectMediaId = projectMediaId; }

        public String getFileName() { return fileName; }
        public void setFileName(String fileName) { this.fileName = fileName; }

        public String getFileType() { return fileType; }
        public void setFileType(String fileType) { this.fileType = fileType; }

        public String getMimeType() { return mimeType; }
        public void setMimeType(String mimeType) { this.mimeType = mimeType; }

        public String getMediaUrl() { return mediaUrl; }
        public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

        public String getCaption() { return caption; }
        public void setCaption(String caption) { this.caption = caption; }

        public Integer getDisplayOrder() { return displayOrder; }
        public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    }

    public static class PublicProjectTechnology {
        private String technologyName;
        private String technologyType;

        public String getTechnologyName() { return technologyName; }
        public void setTechnologyName(String technologyName) { this.technologyName = technologyName; }

        public String getTechnologyType() { return technologyType; }
        public void setTechnologyType(String technologyType) { this.technologyType = technologyType; }
    }
}

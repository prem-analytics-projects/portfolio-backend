
package com.prem.portfolio.media.dto.response;

import java.time.OffsetDateTime;

public class MediaResponseDTO {

    private Long mediaId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String mimeType;
    private Long fileSize;
    private String storageProvider;
    private String altText;
    private Boolean isActive;
    private OffsetDateTime createdAt;

    public MediaResponseDTO() {
    }

    public MediaResponseDTO(
            Long mediaId,
            String fileName,
            String fileUrl,
            String fileType,
            String mimeType,
            Long fileSize,
            String storageProvider,
            String altText,
            Boolean isActive,
            OffsetDateTime createdAt) {

        this.mediaId = mediaId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.storageProvider = storageProvider;
        this.altText = altText;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStorageProvider() {
        return storageProvider;
    }

    public void setStorageProvider(String storageProvider) {
        this.storageProvider = storageProvider;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

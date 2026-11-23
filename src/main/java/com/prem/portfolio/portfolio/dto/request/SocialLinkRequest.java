
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class SocialLinkRequest {

    @NotNull(message = "Profile ID is required")
    @Positive(message = "Profile ID must be positive")
    private Long profileId;

    @NotBlank(message = "Platform name is required")
    @Size(max = 100, message = "Platform name cannot exceed 100 characters")
    private String platformName;

    @NotBlank(message = "Link URL is required")
    @Size(max = 2048, message = "URL cannot exceed 2048 characters")
    @Pattern(
        regexp = "^https?://.+$",
        message = "URL must start with http:// or https://"
    )
    private String linkUrl;

    @Size(max = 100, message = "Icon name cannot exceed 100 characters")
    private String iconName;

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order cannot be negative")
    private Integer displayOrder = 0;

    private Boolean active = true;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
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

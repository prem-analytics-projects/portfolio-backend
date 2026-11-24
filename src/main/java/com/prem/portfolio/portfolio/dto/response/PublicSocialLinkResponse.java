
package com.prem.portfolio.portfolio.dto.response;

public class PublicSocialLinkResponse {

    private Long socialLinkId;
    private Long profileId;
    private String platformName;
    private String linkUrl;
    private String iconName;
    private Integer displayOrder;

    public Long getSocialLinkId() {
        return socialLinkId;
    }

    public void setSocialLinkId(Long socialLinkId) {
        this.socialLinkId = socialLinkId;
    }

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
}

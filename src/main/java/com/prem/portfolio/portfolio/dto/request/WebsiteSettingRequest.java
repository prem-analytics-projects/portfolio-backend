
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class WebsiteSettingRequest {

    @NotBlank(message = "Setting key is required")
    @Size(max = 150, message = "Setting key cannot exceed 150 characters")
    @Pattern(
        regexp = "^[a-zA-Z0-9._-]+$",
        message = "Setting key can contain letters, numbers, dots, underscores and hyphens only"
    )
    private String settingKey;

    private String settingValue;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Public visibility is required")
    private Boolean isPublic = true;

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}

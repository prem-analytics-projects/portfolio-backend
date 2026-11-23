
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ContactDetailRequest {

    @NotNull(message = "Profile ID is required")
    @Positive(message = "Profile ID must be positive")
    private Long profileId;

    @NotBlank(message = "Contact type is required")
    @Size(max = 50, message = "Contact type cannot exceed 50 characters")
    private String contactType;

    @NotBlank(message = "Contact value is required")
    @Size(max = 500, message = "Contact value cannot exceed 500 characters")
    private String contactValue;

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order cannot be negative")
    private Integer displayOrder = 0;

    @NotNull(message = "Active status is required")
    private Boolean active = true;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
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

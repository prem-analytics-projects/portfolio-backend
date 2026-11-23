
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactMessageUpdateRequest {

    @NotBlank(message = "Message status is required")
    @Pattern(
        regexp = "NEW|READ|REPLIED|ARCHIVED|SPAM",
        message = "Invalid message status"
    )
    private String messageStatus;

    private String adminNotes;

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
}

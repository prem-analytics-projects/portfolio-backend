
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactMessageRequest {

    @NotBlank(message = "Sender name is required")
    @Size(max = 150, message = "Sender name cannot exceed 150 characters")
    private String senderName;

    @NotBlank(message = "Sender email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Sender email cannot exceed 255 characters")
    private String senderEmail;

    @Size(max = 255, message = "Subject cannot exceed 255 characters")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

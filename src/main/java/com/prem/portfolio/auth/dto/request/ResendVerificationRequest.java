
package com.prem.portfolio.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email

) {
}

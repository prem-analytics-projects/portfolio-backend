
package com.prem.portfolio.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyEmailRequest(

        @NotBlank(message = "Verification token is required")
        @Size(max = 512, message = "Invalid verification token")
        String token

) {
}

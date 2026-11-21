
package com.prem.portfolio.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(

        @NotBlank(message = "Refresh token is required")
        @Size(max = 512, message = "Invalid refresh token")
        String refreshToken

) {
}

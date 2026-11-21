
package com.prem.portfolio.auth.mapper;

import com.prem.portfolio.auth.dto.response.AuthenticationResponse;
import com.prem.portfolio.auth.dto.response.UserResponse;

public final class AuthenticationMapper {

    private AuthenticationMapper() {
        // Utility class
    }

    public static AuthenticationResponse toResponse(
            String accessToken,
            String refreshToken,
            long expiresIn,
            UserResponse user
    ) {

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                "Bearer",
                expiresIn,
                user
        );
    }
}

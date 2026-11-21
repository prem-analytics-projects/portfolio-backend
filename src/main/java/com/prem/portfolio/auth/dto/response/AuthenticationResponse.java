
package com.prem.portfolio.auth.dto.response;

public record AuthenticationResponse(

        String accessToken,

        String refreshToken,

        String tokenType,

        long expiresIn,

        UserResponse user

) {
}


package com.prem.portfolio.auth.mapper;

import com.prem.portfolio.auth.dto.response.UserResponse;
import com.prem.portfolio.auth.entity.User;

import java.util.List;

public final class UserMapper {

    private UserMapper() {
        // Utility class
    }

    public static UserResponse toResponse(
            User user,
            List<String> roles
    ) {

        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobile(),
                user.getEmailVerifiedAt(),
                user.getAccountStatus(),
                roles == null ? List.of() : List.copyOf(roles)
        );
    }
}


package com.prem.portfolio.auth.dto.response;

import com.prem.portfolio.auth.enums.AccountStatus;

import java.time.OffsetDateTime;
import java.util.List;

public record UserResponse(

        Long userId,

        String firstName,

        String lastName,

        String email,

        String mobile,

        OffsetDateTime emailVerifiedAt,

        AccountStatus accountStatus,

        List<String> roles

) {
}

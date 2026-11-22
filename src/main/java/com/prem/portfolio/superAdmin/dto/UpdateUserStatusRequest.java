
package com.prem.portfolio.superadmin.dto;

import com.prem.portfolio.auth.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(

        @NotNull(message = "Account status is required")
        AccountStatus accountStatus

) {
}

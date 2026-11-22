
package com.prem.portfolio.superadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUserRoleRequest(

        @NotBlank(message = "Role name is required")
        @Pattern(
                regexp = "(?i)USER|EDITOR|ADMIN|SUPER_ADMIN",
                message = "Role must be USER, EDITOR, ADMIN or SUPER_ADMIN"
        )
        String roleName

) {
}


package com.prem.portfolio.superadmin.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record SuperAdminUserResponse(
        Long userId,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String mobile,
        OffsetDateTime emailVerifiedAt,
        String accountStatus,
        OffsetDateTime createdAt,
        List<RoleAssignment> roles
) {
    public record RoleAssignment(
            String roleName,
            String approvalStatus,
            OffsetDateTime requestedAt,
            OffsetDateTime approvedAt,
            String rejectionReason
    ) {
    }
}

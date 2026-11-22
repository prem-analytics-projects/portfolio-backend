
package com.prem.portfolio.superadmin.service;

import java.util.List;

import com.prem.portfolio.auth.enums.AccountStatus;
import com.prem.portfolio.superadmin.dto.SuperAdminUserResponse;

public interface SuperAdminService {

    List<SuperAdminUserResponse> getAllUsers();

    SuperAdminUserResponse getUserById(Long userId);

    SuperAdminUserResponse approveRegistration(Long userId);

    SuperAdminUserResponse rejectRegistration(
            Long userId,
            String reason
    );

    SuperAdminUserResponse updateUserRole(
            Long userId,
            String roleName
    );

    SuperAdminUserResponse updateUserStatus(
            Long userId,
            AccountStatus accountStatus
    );
}

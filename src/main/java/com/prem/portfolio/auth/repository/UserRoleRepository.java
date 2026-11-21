
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.UserRole;
import com.prem.portfolio.auth.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUser_UserId(Long userId);

    List<UserRole> findByUser_UserIdAndApprovalStatus(
            Long userId,
            ApprovalStatus approvalStatus
    );

    List<UserRole> findByApprovalStatus(ApprovalStatus approvalStatus);

    Optional<UserRole> findByUser_UserIdAndRole_RoleId(
            Long userId,
            Long roleId
    );

    boolean existsByUser_UserIdAndRole_RoleIdAndApprovalStatus(
            Long userId,
            Long roleId,
            ApprovalStatus approvalStatus
    );
}

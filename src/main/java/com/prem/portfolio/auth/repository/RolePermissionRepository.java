
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.RolePermission;
import com.prem.portfolio.auth.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository
        extends JpaRepository<RolePermission, RolePermissionId> {

    List<RolePermission> findByRole_RoleId(Long roleId);

    List<RolePermission> findByPermission_PermissionId(Long permissionId);

    boolean existsByRole_RoleIdAndPermission_PermissionId(
            Long roleId,
            Long permissionId
    );

    void deleteByRole_RoleIdAndPermission_PermissionId(
            Long roleId,
            Long permissionId
    );
}

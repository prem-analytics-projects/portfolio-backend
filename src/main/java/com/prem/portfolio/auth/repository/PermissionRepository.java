
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissionKey(String permissionKey);

    boolean existsByPermissionKey(String permissionKey);
}

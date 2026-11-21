
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleNameIgnoreCase(String roleName);

    boolean existsByRoleNameIgnoreCase(String roleName);
}

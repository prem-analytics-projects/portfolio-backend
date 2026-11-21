
package com.prem.portfolio.auth.service;

import com.prem.portfolio.auth.entity.Role;
import com.prem.portfolio.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRequiredRole(String roleName) {

        return roleRepository
                .findByRoleNameIgnoreCase(roleName)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Role not found: " + roleName
                        )
                );
    }

    public boolean roleExists(String roleName) {

        return roleRepository
                .existsByRoleNameIgnoreCase(roleName);
    }
}

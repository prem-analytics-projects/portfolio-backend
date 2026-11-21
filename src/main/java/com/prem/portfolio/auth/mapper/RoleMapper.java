
package com.prem.portfolio.auth.mapper;

import com.prem.portfolio.auth.entity.Role;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class RoleMapper {

    private RoleMapper() {
        // Utility class
    }

    public static String toRoleName(Role role) {

        if (role == null) {
            return null;
        }

        return role.getRoleName();
    }

    public static List<String> toRoleNames(List<Role> roles) {

        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        return roles.stream()
                .filter(Objects::nonNull)
                .map(RoleMapper::toRoleName)
                .toList();
    }
}

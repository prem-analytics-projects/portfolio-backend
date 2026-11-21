
package com.prem.portfolio.superAdmin.bootstrap;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@ConditionalOnProperty(
        prefix = "app.bootstrap.super-admin",
        name = "enabled",
        havingValue = "true"
)
public class SuperAdminBootstrap implements ApplicationRunner {

    private static final Logger log =
            LoggerFactory.getLogger(SuperAdminBootstrap.class);

    private static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    private final String email;
    private final String password;

    public SuperAdminBootstrap(
            JdbcTemplate jdbcTemplate,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap.super-admin.email:}") String email,
            @Value("${app.bootstrap.super-admin.password:}") String password) {

        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        validateConfiguration();

        String normalizedEmail =
                email.trim().toLowerCase(Locale.ROOT);

        // Prevent simultaneous bootstrap executions.
        jdbcTemplate.execute(
                "SELECT pg_advisory_xact_lock(82100421)"
        );

        // Find the existing SUPER_ADMIN role.
        List<Long> roleIds = jdbcTemplate.queryForList(
                """
                SELECT role_id
                FROM auth.roles
                WHERE role_name = ?
                """,
                Long.class,
                SUPER_ADMIN_ROLE
        );

        if (roleIds.isEmpty()) {
            throw new IllegalStateException(
                    "SUPER_ADMIN role was not found in auth.roles."
            );
        }

        Long roleId = roleIds.get(0);

        // Check whether the configured email already exists.
        List<Long> existingUserIds = jdbcTemplate.queryForList(
                """
                SELECT user_id
                FROM auth.users
                WHERE LOWER(email) = LOWER(?)
                """,
                Long.class,
                normalizedEmail
        );

        if (!existingUserIds.isEmpty()) {

            Long existingUserId = existingUserIds.get(0);

            Integer validAssignments = jdbcTemplate.queryForObject(
                    """
                    SELECT COUNT(*)
                    FROM auth.users u
                    JOIN auth.user_roles ur
                        ON ur.user_id = u.user_id
                    WHERE u.user_id = ?
                      AND ur.role_id = ?
                      AND ur.approval_status = 'APPROVED'
                      AND u.account_status = 'ACTIVE'
                      AND u.email_verified_at IS NOT NULL
                    """,
                    Integer.class,
                    existingUserId,
                    roleId
            );

            if (validAssignments != null && validAssignments == 1) {
                log.info(
                        "Super Admin already initialized. No changes made."
                );
                return;
            }

            throw new IllegalStateException(
                    "The configured email already exists but is not "
                    + "an active, verified Super Admin. "
                    + "Manual database review is required."
            );
        }

        // Encode the password using the existing application encoder.
        String passwordHash = passwordEncoder.encode(password);

        // Create the initial Super Admin account.
        Long userId = jdbcTemplate.queryForObject(
                """
                INSERT INTO auth.users (
                    first_name,
                    middle_name,
                    last_name,
                    email,
                    password_hash,
                    email_verified_at,
                    account_status,
                    created_at,
                    updated_at
                )
                VALUES (
                    ?, ?, ?, ?, ?,
                    CURRENT_TIMESTAMP,
                    'ACTIVE',
                    CURRENT_TIMESTAMP,
                    CURRENT_TIMESTAMP
                )
                RETURNING user_id
                """,
                Long.class,
                "Prem",
                "Singh",
                "Kuntia",
                normalizedEmail,
                passwordHash
        );

        if (userId == null) {
            throw new IllegalStateException(
                    "Unable to create the Super Admin account."
            );
        }

        // Assign the existing SUPER_ADMIN role.
        jdbcTemplate.update(
                """
                INSERT INTO auth.user_roles (
                    user_id,
                    role_id,
                    approval_status,
                    requested_at,
                    approved_at,
                    approved_by,
                    created_at,
                    updated_at
                )
                VALUES (
                    ?, ?,
                    'APPROVED',
                    CURRENT_TIMESTAMP,
                    CURRENT_TIMESTAMP,
                    NULL,
                    CURRENT_TIMESTAMP,
                    CURRENT_TIMESTAMP
                )
                """,
                userId,
                roleId
        );

        log.info(
                "Initial Super Admin account created successfully. "
                + "User ID: {}",
                userId
        );
    }

    private void validateConfiguration() {

        if (!StringUtils.hasText(email)) {
            throw new IllegalStateException(
                    "SUPER_ADMIN_EMAIL is not configured."
            );
        }

        if (!StringUtils.hasText(password)) {
            throw new IllegalStateException(
                    "SUPER_ADMIN_PASSWORD is not configured."
            );
        }

        if (password.length() < 16) {
            throw new IllegalStateException(
                    "Super Admin password must contain "
                    + "at least 16 characters."
            );
        }
    }
}


package com.prem.portfolio.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prem.portfolio.portfolio.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    // Search profiles by name/email and optionally filter by active status
    @Query("""
        SELECT p FROM Profile p
        WHERE (:active IS NULL OR p.active = :active)
        AND (
            :search IS NULL
            OR TRIM(:search) = ''
            OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    Page<Profile> searchProfiles(
            @Param("search") String search,
            @Param("active") Boolean active,
            Pageable pageable
    );
}

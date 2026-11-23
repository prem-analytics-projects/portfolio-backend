
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("""
            SELECT e
            FROM Experience e
            WHERE e.profileId = :profileId
              AND (:active IS NULL OR e.active = :active)
            ORDER BY e.displayOrder ASC, e.experienceId ASC
            """)
    List<Experience> findExperiences(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active
    );

    Optional<Experience> findByExperienceIdAndActiveTrue(Long experienceId);

    boolean existsByProfileIdAndCurrentTrueAndActiveTrue(Long profileId);
}

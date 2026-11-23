
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query("""
            SELECT e
            FROM Education e
            WHERE e.profileId = :profileId
              AND (:active IS NULL OR e.active = :active)
            ORDER BY e.displayOrder ASC, e.educationId ASC
            """)
    List<Education> findEducations(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active
    );

    Optional<Education> findByEducationIdAndActiveTrue(Long educationId);

    boolean existsByProfileIdAndInstitutionNameIgnoreCaseAndDegreeIgnoreCaseAndActiveTrue(
            Long profileId,
            String institutionName,
            String degree
    );
}

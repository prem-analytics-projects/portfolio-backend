
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p FROM Project p
        WHERE p.profileId = :profileId
        AND (:active IS NULL OR p.active = :active)
        AND (:featured IS NULL OR p.featured = :featured)
        ORDER BY p.displayOrder ASC, p.projectId ASC
        """)
    List<Project> findProjects(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active,
            @Param("featured") Boolean featured
    );

    Optional<Project> findByProjectIdAndActiveTrue(Long projectId);

    boolean existsByProfileIdAndTitleIgnoreCaseAndActiveTrue(
            Long profileId,
            String title
    );
}

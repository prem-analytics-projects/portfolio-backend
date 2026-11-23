
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.ProjectMedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMediaRepository
        extends JpaRepository<ProjectMedia, Long> {

    @Query("""
        SELECT pm FROM ProjectMedia pm
        WHERE pm.projectId = :projectId
        AND (:active IS NULL OR pm.active = :active)
        ORDER BY pm.displayOrder ASC, pm.projectMediaId ASC
        """)
    List<ProjectMedia> findProjectMedia(
            @Param("projectId") Long projectId,
            @Param("active") Boolean active
    );

    Optional<ProjectMedia>
            findByProjectMediaIdAndActiveTrue(Long projectMediaId);

    boolean existsByProjectIdAndMediaIdAndActiveTrue(
            Long projectId,
            Long mediaId
    );
}

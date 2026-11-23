
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.ProjectImpactMetric;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectImpactMetricRepository
        extends JpaRepository<ProjectImpactMetric, Long> {

    @Query("""
        SELECT m FROM ProjectImpactMetric m
        WHERE m.projectId = :projectId
        AND (:active IS NULL OR m.active = :active)
        ORDER BY m.displayOrder ASC, m.impactMetricId ASC
        """)
    List<ProjectImpactMetric> findMetrics(
            @Param("projectId") Long projectId,
            @Param("active") Boolean active
    );

    Optional<ProjectImpactMetric>
            findByImpactMetricIdAndActiveTrue(Long impactMetricId);

}

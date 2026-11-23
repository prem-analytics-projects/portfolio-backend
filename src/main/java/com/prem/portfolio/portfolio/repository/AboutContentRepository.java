
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.AboutContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AboutContentRepository extends JpaRepository<AboutContent, Long> {

    @Query("""
        SELECT a FROM AboutContent a
        WHERE (:profileId IS NULL OR a.profileId = :profileId)
        AND (:active IS NULL OR a.active = :active)
        ORDER BY a.displayOrder ASC, a.aboutId ASC
        """)
    List<AboutContent> findAboutContent(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active
    );
}

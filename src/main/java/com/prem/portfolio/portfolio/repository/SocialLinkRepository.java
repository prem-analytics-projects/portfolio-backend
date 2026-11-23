
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.SocialLink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {

    @Query("""
            SELECT s
            FROM SocialLink s
            WHERE s.profileId = :profileId
              AND (:active IS NULL OR s.active = :active)
            ORDER BY s.displayOrder ASC, s.socialLinkId ASC
            """)
    List<SocialLink> findSocialLinks(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active
    );

    Optional<SocialLink> findBySocialLinkIdAndActiveTrue(Long socialLinkId);
}

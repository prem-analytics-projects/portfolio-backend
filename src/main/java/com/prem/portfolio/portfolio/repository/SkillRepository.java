
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("""
        SELECT s FROM Skill s
        WHERE (:profileId IS NULL OR s.profileId = :profileId)
        AND (:skillCategoryId IS NULL OR s.skillCategoryId = :skillCategoryId)
        AND (:active IS NULL OR s.active = :active)
        ORDER BY s.displayOrder ASC, s.skillId ASC
        """)
    List<Skill> findSkills(
            @Param("profileId") Long profileId,
            @Param("skillCategoryId") Long skillCategoryId,
            @Param("active") Boolean active
    );

    boolean existsByProfileIdAndSkillNameIgnoreCaseAndActiveTrue(
            Long profileId,
            String skillName
    );
}

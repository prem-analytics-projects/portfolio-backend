
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    @Query("""
            SELECT l
            FROM Language l
            WHERE l.profileId = :profileId
              AND (:active IS NULL OR l.active = :active)
            ORDER BY l.displayOrder ASC, l.languageId ASC
            """)
    List<Language> findLanguages(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active
    );

    Optional<Language> findByLanguageIdAndActiveTrue(Long languageId);

    boolean existsByProfileIdAndLanguageNameIgnoreCaseAndActiveTrue(
            Long profileId,
            String languageName
    );
}

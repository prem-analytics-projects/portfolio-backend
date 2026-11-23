
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {

    boolean existsByCategoryNameIgnoreCase(String categoryName);

    Optional<SkillCategory> findByCategoryNameIgnoreCase(String categoryName);

    List<SkillCategory> findAllByOrderByDisplayOrderAscCategoryNameAsc();

    List<SkillCategory> findByActiveOrderByDisplayOrderAscCategoryNameAsc(Boolean active);
}

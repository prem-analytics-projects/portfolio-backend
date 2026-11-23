
package com.prem.portfolio.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prem.portfolio.portfolio.entity.BlogCategory;

public interface BlogCategoryRepository
        extends JpaRepository<BlogCategory, Long> {

    List<BlogCategory> findByActive(Boolean active);

    Optional<BlogCategory> findByBlogCategoryIdAndActiveTrue(
            Long blogCategoryId
    );

    Optional<BlogCategory> findByCategoryNameIgnoreCase(
            String categoryName
    );

    boolean existsByCategoryNameIgnoreCase(String categoryName);
}

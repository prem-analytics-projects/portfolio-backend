
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.BlogCategoryMapping;
import com.prem.portfolio.portfolio.entity.BlogCategoryMappingId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCategoryMappingRepository
        extends JpaRepository<BlogCategoryMapping, BlogCategoryMappingId> {

    List<BlogCategoryMapping> findByBlogId(Long blogId);

    boolean existsByBlogIdAndBlogCategoryId(Long blogId, Long blogCategoryId);

    void deleteByBlogIdAndBlogCategoryId(Long blogId, Long blogCategoryId);

    void deleteByBlogId(Long blogId);
}

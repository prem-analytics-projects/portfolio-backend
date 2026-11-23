
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.BlogTagMapping;
import com.prem.portfolio.portfolio.entity.BlogTagMappingId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogTagMappingRepository
        extends JpaRepository<BlogTagMapping, BlogTagMappingId> {

    List<BlogTagMapping> findByBlogId(Long blogId);

    boolean existsByBlogIdAndBlogTagId(Long blogId, Long blogTagId);

    void deleteByBlogIdAndBlogTagId(Long blogId, Long blogTagId);

    void deleteByBlogId(Long blogId);
}

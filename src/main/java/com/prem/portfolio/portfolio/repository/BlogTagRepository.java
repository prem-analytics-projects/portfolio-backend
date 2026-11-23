
package com.prem.portfolio.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prem.portfolio.portfolio.entity.BlogTag;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {

    Optional<BlogTag> findByTagNameIgnoreCase(String tagName);

    boolean existsByTagNameIgnoreCase(String tagName);
}

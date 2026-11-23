
package com.prem.portfolio.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prem.portfolio.portfolio.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findBySlugIgnoreCase(String slug);

    boolean existsBySlugIgnoreCase(String slug);

    Optional<Blog> findByBlogIdAndActiveTrue(Long blogId);

    @Query("""
        SELECT b FROM Blog b
        WHERE (:profileId IS NULL OR b.profileId = :profileId)
        AND (:active IS NULL OR b.active = :active)
        AND (:publicationStatus IS NULL
             OR b.publicationStatus = :publicationStatus)
        ORDER BY b.displayOrder ASC, b.blogId DESC
        """)
    List<Blog> findBlogs(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active,
            @Param("publicationStatus") String publicationStatus
    );
}

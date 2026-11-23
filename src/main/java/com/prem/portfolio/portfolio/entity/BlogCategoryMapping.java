
package com.prem.portfolio.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "blog_category_mapping", schema = "portfolio")
@IdClass(BlogCategoryMappingId.class)
public class BlogCategoryMapping {

    @Id
    @Column(name = "blog_id")
    private Long blogId;

    @Id
    @Column(name = "blog_category_id")
    private Long blogCategoryId;

    public BlogCategoryMapping() {
    }

    public BlogCategoryMapping(Long blogId, Long blogCategoryId) {
        this.blogId = blogId;
        this.blogCategoryId = blogCategoryId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getBlogCategoryId() {
        return blogCategoryId;
    }

    public void setBlogCategoryId(Long blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
    }
}

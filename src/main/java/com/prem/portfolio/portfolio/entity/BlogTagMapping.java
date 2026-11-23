
package com.prem.portfolio.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "blog_tag_mapping", schema = "portfolio")
@IdClass(BlogTagMappingId.class)
public class BlogTagMapping {

    @Id
    @Column(name = "blog_id")
    private Long blogId;

    @Id
    @Column(name = "blog_tag_id")
    private Long blogTagId;

    public BlogTagMapping() {
    }

    public BlogTagMapping(Long blogId, Long blogTagId) {
        this.blogId = blogId;
        this.blogTagId = blogTagId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(Long blogTagId) {
        this.blogTagId = blogTagId;
    }
}

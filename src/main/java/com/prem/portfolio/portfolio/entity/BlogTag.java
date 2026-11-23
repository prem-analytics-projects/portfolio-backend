
package com.prem.portfolio.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blog_tags", schema = "portfolio")
public class BlogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_tag_id")
    private Long blogTagId;

    @Column(name = "tag_name", nullable = false, unique = true, length = 100)
    private String tagName;

    public Long getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(Long blogTagId) {
        this.blogTagId = blogTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

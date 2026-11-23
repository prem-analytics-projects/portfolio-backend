
package com.prem.portfolio.portfolio.entity;

import java.io.Serializable;
import java.util.Objects;

public class BlogTagMappingId implements Serializable {

    private Long blogId;
    private Long blogTagId;

    public BlogTagMappingId() {
    }

    public BlogTagMappingId(Long blogId, Long blogTagId) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BlogTagMappingId other)) return false;

        return Objects.equals(blogId, other.blogId)
                && Objects.equals(blogTagId, other.blogTagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId, blogTagId);
    }
}

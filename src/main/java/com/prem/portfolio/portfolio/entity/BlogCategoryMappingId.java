
package com.prem.portfolio.portfolio.entity;

import java.io.Serializable;
import java.util.Objects;

public class BlogCategoryMappingId implements Serializable {

    private Long blogId;
    private Long blogCategoryId;

    public BlogCategoryMappingId() {
    }

    public BlogCategoryMappingId(Long blogId, Long blogCategoryId) {
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

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof BlogCategoryMappingId other)) {
            return false;
        }

        return Objects.equals(blogId, other.blogId)
                && Objects.equals(blogCategoryId, other.blogCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId, blogCategoryId);
    }
}

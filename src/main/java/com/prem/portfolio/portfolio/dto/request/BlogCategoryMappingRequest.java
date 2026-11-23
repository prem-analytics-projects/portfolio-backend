
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BlogCategoryMappingRequest {

    @NotNull(message = "Blog category ID is required")
    @Positive(message = "Blog category ID must be positive")
    private Long blogCategoryId;

    public Long getBlogCategoryId() {
        return blogCategoryId;
    }

    public void setBlogCategoryId(Long blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
    }
}


package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BlogTagMappingRequest {

    @NotNull(message = "Blog tag ID is required")
    @Positive(message = "Blog tag ID must be positive")
    private Long blogTagId;

    public Long getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(Long blogTagId) {
        this.blogTagId = blogTagId;
    }
}

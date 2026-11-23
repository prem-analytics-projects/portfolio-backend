
package com.prem.portfolio.portfolio.dto.response;

public class BlogTagResponse {

    private Long blogTagId;
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

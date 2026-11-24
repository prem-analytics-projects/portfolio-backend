
package com.prem.portfolio.portfolio.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public class PublicBlogResponse {

    private Long blogId;
    private Long profileId;
    private String title;
    private String slug;
    private String summary;
    private String content;
    private String coverImageUrl;
    private OffsetDateTime publishedAt;
    private Integer displayOrder;

    private List<PublicBlogCategory> categories;
    private List<PublicBlogTag> tags;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(OffsetDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<PublicBlogCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PublicBlogCategory> categories) {
        this.categories = categories;
    }

    public List<PublicBlogTag> getTags() {
        return tags;
    }

    public void setTags(List<PublicBlogTag> tags) {
        this.tags = tags;
    }

    public static class PublicBlogCategory {

        private Long blogCategoryId;
        private String categoryName;
        private String description;

        public Long getBlogCategoryId() {
            return blogCategoryId;
        }

        public void setBlogCategoryId(Long blogCategoryId) {
            this.blogCategoryId = blogCategoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class PublicBlogTag {

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
}

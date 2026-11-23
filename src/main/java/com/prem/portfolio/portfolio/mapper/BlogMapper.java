
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.BlogRequest;
import com.prem.portfolio.portfolio.dto.response.BlogResponse;
import com.prem.portfolio.portfolio.entity.Blog;

@Component
public class BlogMapper {

    public Blog toEntity(BlogRequest request) {

        Blog blog = new Blog();

        blog.setProfileId(request.getProfileId());
        blog.setTitle(request.getTitle().trim());
        blog.setSlug(request.getSlug().trim());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setCoverMediaId(request.getCoverMediaId());
        blog.setPublicationStatus(
                request.getPublicationStatus().trim().toUpperCase()
        );
        blog.setPublishedAt(request.getPublishedAt());
        blog.setActive(request.getActive());
        blog.setDisplayOrder(request.getDisplayOrder());

        return blog;
    }

    public void updateEntity(Blog blog, BlogRequest request) {

        blog.setProfileId(request.getProfileId());
        blog.setTitle(request.getTitle().trim());
        blog.setSlug(request.getSlug().trim());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setCoverMediaId(request.getCoverMediaId());
        blog.setPublicationStatus(
                request.getPublicationStatus().trim().toUpperCase()
        );
        blog.setPublishedAt(request.getPublishedAt());
        blog.setActive(request.getActive());
        blog.setDisplayOrder(request.getDisplayOrder());
    }

    public BlogResponse toResponse(Blog blog) {

        BlogResponse response = new BlogResponse();

        response.setBlogId(blog.getBlogId());
        response.setProfileId(blog.getProfileId());
        response.setTitle(blog.getTitle());
        response.setSlug(blog.getSlug());
        response.setSummary(blog.getSummary());
        response.setContent(blog.getContent());

        response.setCoverMediaId(blog.getCoverMediaId());

        response.setPublicationStatus(blog.getPublicationStatus());
        response.setPublishedAt(blog.getPublishedAt());

        response.setActive(blog.getActive());
        response.setDisplayOrder(blog.getDisplayOrder());

        response.setCreatedAt(blog.getCreatedAt());
        response.setUpdatedAt(blog.getUpdatedAt());

        response.setCreatedBy(blog.getCreatedBy());
        response.setUpdatedBy(blog.getUpdatedBy());

        return response;
    }
}

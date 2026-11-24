
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prem.portfolio.portfolio.dto.response.BlogCategoryMappingResponse;
import com.prem.portfolio.portfolio.dto.response.BlogResponse;
import com.prem.portfolio.portfolio.dto.response.BlogTagMappingResponse;
import com.prem.portfolio.portfolio.dto.response.PublicBlogResponse;
import com.prem.portfolio.portfolio.dto.response.PublicBlogResponse.PublicBlogCategory;
import com.prem.portfolio.portfolio.dto.response.PublicBlogResponse.PublicBlogTag;
import com.prem.portfolio.portfolio.service.BlogService;

@RestController
@RequestMapping("/api/v1/public/blogs")
public class PublicBlogController {

    private final BlogService blogService;

    public PublicBlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<PublicBlogResponse>> getPublicBlogs(
            @RequestParam Long profileId) {

        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException(
                    "Profile ID must be a positive number");
        }

        List<BlogResponse> blogs = blogService.getBlogs(
                profileId,
                true,
                "PUBLISHED"
        );

        List<PublicBlogResponse> response = blogs.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicBlogResponse toPublicResponse(BlogResponse source) {

        PublicBlogResponse response = new PublicBlogResponse();

        response.setBlogId(source.getBlogId());
        response.setProfileId(source.getProfileId());
        response.setTitle(source.getTitle());
        response.setSlug(source.getSlug());
        response.setSummary(source.getSummary());
        response.setContent(source.getContent());
        response.setCoverImageUrl(source.getCoverImageUrl());
        response.setPublishedAt(source.getPublishedAt());
        response.setDisplayOrder(source.getDisplayOrder());

        List<PublicBlogCategory> categories =
                source.getCategories() == null
                ? List.of()
                : source.getCategories().stream()
                    .filter(category ->
                            Boolean.TRUE.equals(category.getActive()))
                    .map(this::toPublicCategory)
                    .toList();

        List<PublicBlogTag> tags =
                source.getTags() == null
                ? List.of()
                : source.getTags().stream()
                    .map(this::toPublicTag)
                    .toList();

        response.setCategories(categories);
        response.setTags(tags);

        return response;
    }

    private PublicBlogCategory toPublicCategory(
            BlogCategoryMappingResponse source) {

        PublicBlogCategory category = new PublicBlogCategory();

        category.setBlogCategoryId(source.getBlogCategoryId());
        category.setCategoryName(source.getCategoryName());
        category.setDescription(source.getDescription());

        return category;
    }

    private PublicBlogTag toPublicTag(
            BlogTagMappingResponse source) {

        PublicBlogTag tag = new PublicBlogTag();

        tag.setBlogTagId(source.getBlogTagId());
        tag.setTagName(source.getTagName());

        return tag;
    }
}

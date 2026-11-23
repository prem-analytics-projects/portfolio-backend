
package com.prem.portfolio.portfolio.service.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.request.BlogRequest;
import com.prem.portfolio.portfolio.dto.response.BlogResponse;
import com.prem.portfolio.portfolio.entity.Blog;
import com.prem.portfolio.portfolio.mapper.BlogMapper;
import com.prem.portfolio.portfolio.repository.BlogRepository;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.BlogCategoryMappingService;
import com.prem.portfolio.portfolio.service.BlogService;
import com.prem.portfolio.portfolio.service.BlogTagMappingService;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final ProfileRepository profileRepository;
    private final MediaRepository mediaRepository;
    private final BlogMapper blogMapper;
    private final BlogCategoryMappingService blogCategoryMappingService;
    private final BlogTagMappingService blogTagMappingService;

    public BlogServiceImpl(
            BlogRepository blogRepository,
            ProfileRepository profileRepository,
            MediaRepository mediaRepository,
            BlogMapper blogMapper,
            BlogCategoryMappingService blogCategoryMappingService,
            BlogTagMappingService blogTagMappingService) {

        this.blogRepository = blogRepository;
        this.profileRepository = profileRepository;
        this.mediaRepository = mediaRepository;
        this.blogMapper = blogMapper;
        this.blogCategoryMappingService = blogCategoryMappingService;
        this.blogTagMappingService = blogTagMappingService;
    }

    @Override
    public BlogResponse createBlog(BlogRequest request) {

        validateProfile(request.getProfileId());
        validateCoverMedia(request.getCoverMediaId());
        validatePublicationStatus(request.getPublicationStatus());

        String slug = normalizeSlug(request.getSlug());
        validateSlugUniqueness(slug, null);

        Blog blog = blogMapper.toEntity(request);
        blog.setSlug(slug);

        if ("PUBLISHED".equals(blog.getPublicationStatus())
                && blog.getPublishedAt() == null) {
            blog.setPublishedAt(OffsetDateTime.now());
        }

        Blog savedBlog = blogRepository.save(blog);

        return buildResponse(savedBlog);
    }

    @Override
    public BlogResponse updateBlog(Long blogId, BlogRequest request) {

        Blog blog = blogRepository.findByBlogIdAndActiveTrue(blogId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Active blog not found"));

        validateProfile(request.getProfileId());
        validateCoverMedia(request.getCoverMediaId());
        validatePublicationStatus(request.getPublicationStatus());

        String slug = normalizeSlug(request.getSlug());
        validateSlugUniqueness(slug, blogId);

        blogMapper.updateEntity(blog, request);
        blog.setSlug(slug);

        if ("PUBLISHED".equals(blog.getPublicationStatus())
                && blog.getPublishedAt() == null) {
            blog.setPublishedAt(OffsetDateTime.now());
        }

        Blog updatedBlog = blogRepository.save(blog);

        return buildResponse(updatedBlog);
    }

    @Override
    @Transactional(readOnly = true)
    public BlogResponse getBlogById(Long blogId) {

        Blog blog = blogRepository.findByBlogIdAndActiveTrue(blogId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Active blog not found"));

        return buildResponse(blog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogResponse> getBlogs(
            Long profileId,
            Boolean active,
            String publicationStatus) {

        String status = publicationStatus == null
                || publicationStatus.isBlank()
                ? null
                : publicationStatus.trim().toUpperCase();

        if (status != null) {
            validatePublicationStatus(status);
        }

        if (profileId != null) {
            validateProfile(profileId);
        }

        return blogRepository.findBlogs(profileId, active, status)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void deleteBlog(Long blogId) {

        Blog blog = blogRepository.findByBlogIdAndActiveTrue(blogId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Active blog not found"));

        blog.setActive(false);

        blogRepository.save(blog);
    }

    private void validateProfile(Long profileId) {

        boolean exists = profileRepository
                .findByProfileIdAndActiveTrue(profileId)
                .isPresent();

        if (!exists) {
            throw new IllegalArgumentException(
                    "Active profile not found: " + profileId);
        }
    }

    private void validateCoverMedia(Long coverMediaId) {

        if (coverMediaId == null) {
            return;
        }

        MediaAsset media = mediaRepository.findById(coverMediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cover media not found"));

        if (!Boolean.TRUE.equals(media.getIsActive())) {
            throw new IllegalArgumentException(
                    "Cover media is inactive");
        }

        if (!"IMAGE".equalsIgnoreCase(media.getFileType())) {
            throw new IllegalArgumentException(
                    "Blog cover media must be an IMAGE");
        }
    }

    private void validatePublicationStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Publication status is required");
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!List.of("DRAFT", "PUBLISHED", "ARCHIVED")
                .contains(normalizedStatus)) {
            throw new IllegalArgumentException(
                    "Invalid publication status. Allowed values: "
                    + "DRAFT, PUBLISHED, ARCHIVED");
        }
    }

    private String normalizeSlug(String slug) {

        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Blog slug is required");
        }

        return slug.trim().toLowerCase();
    }

    private void validateSlugUniqueness(
            String slug,
            Long currentBlogId) {

        blogRepository.findBySlugIgnoreCase(slug)
                .ifPresent(existingBlog -> {

                    if (currentBlogId == null
                            || !existingBlog.getBlogId().equals(currentBlogId)) {

                        throw new IllegalArgumentException(
                                "Blog slug already exists: " + slug);
                    }
                });
    }

    private BlogResponse buildResponse(Blog blog) {

        BlogResponse response = blogMapper.toResponse(blog);

        // Populate cover image URL
        if (blog.getCoverMediaId() != null) {

            mediaRepository.findById(blog.getCoverMediaId())
                    .filter(media ->
                            Boolean.TRUE.equals(media.getIsActive()))
                    .ifPresent(media ->
                            response.setCoverImageUrl(media.getFileUrl()));
        }

        // Populate assigned categories
        response.setCategories(
                blogCategoryMappingService.getBlogCategories(
                        blog.getBlogId()));

        // Populate assigned tags
        response.setTags(
                blogTagMappingService.getBlogTags(
                        blog.getBlogId()));

        return response;
    }
}

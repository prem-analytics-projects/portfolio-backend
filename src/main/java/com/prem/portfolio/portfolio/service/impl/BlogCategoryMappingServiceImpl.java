
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.BlogCategoryMappingRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryMappingResponse;
import com.prem.portfolio.portfolio.entity.BlogCategory;
import com.prem.portfolio.portfolio.entity.BlogCategoryMapping;
import com.prem.portfolio.portfolio.mapper.BlogCategoryMappingMapper;
import com.prem.portfolio.portfolio.repository.BlogCategoryMappingRepository;
import com.prem.portfolio.portfolio.repository.BlogCategoryRepository;
import com.prem.portfolio.portfolio.repository.BlogRepository;
import com.prem.portfolio.portfolio.service.BlogCategoryMappingService;

@Service
@Transactional
public class BlogCategoryMappingServiceImpl
        implements BlogCategoryMappingService {

    private final BlogCategoryMappingRepository mappingRepository;
    private final BlogRepository blogRepository;
    private final BlogCategoryRepository categoryRepository;
    private final BlogCategoryMappingMapper mappingMapper;

    public BlogCategoryMappingServiceImpl(
            BlogCategoryMappingRepository mappingRepository,
            BlogRepository blogRepository,
            BlogCategoryRepository categoryRepository,
            BlogCategoryMappingMapper mappingMapper) {

        this.mappingRepository = mappingRepository;
        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.mappingMapper = mappingMapper;
    }

    @Override
    public BlogCategoryMappingResponse addCategory(
            Long blogId,
            BlogCategoryMappingRequest request) {

        validateActiveBlog(blogId);

        Long categoryId = request.getBlogCategoryId();

        BlogCategory category = categoryRepository
                .findByBlogCategoryIdAndActiveTrue(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active blog category not found: " + categoryId));

        if (mappingRepository.existsByBlogIdAndBlogCategoryId(
                blogId, categoryId)) {

            throw new IllegalArgumentException(
                    "This category is already assigned to the blog");
        }

        BlogCategoryMapping mapping =
                mappingMapper.toEntity(blogId, categoryId);

        BlogCategoryMapping savedMapping =
                mappingRepository.save(mapping);

        return mappingMapper.toResponse(savedMapping, category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogCategoryMappingResponse> getBlogCategories(
            Long blogId) {

        validateActiveBlog(blogId);

        return mappingRepository.findByBlogId(blogId)
                .stream()
                .map(mapping -> {
                    BlogCategory category = categoryRepository
                            .findById(mapping.getBlogCategoryId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Blog category not found: "
                                            + mapping.getBlogCategoryId()));

                    return mappingMapper.toResponse(mapping, category);
                })
                .toList();
    }

    @Override
    public void removeCategory(Long blogId, Long blogCategoryId) {

        validateActiveBlog(blogId);

        if (!mappingRepository.existsByBlogIdAndBlogCategoryId(
                blogId, blogCategoryId)) {

            throw new IllegalArgumentException(
                    "Category is not assigned to this blog");
        }

        mappingRepository.deleteByBlogIdAndBlogCategoryId(
                blogId, blogCategoryId);
    }

    private void validateActiveBlog(Long blogId) {

        blogRepository.findByBlogIdAndActiveTrue(blogId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active blog not found: " + blogId));
    }
}

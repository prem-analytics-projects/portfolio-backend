
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.BlogTagMappingRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagMappingResponse;
import com.prem.portfolio.portfolio.entity.BlogTag;
import com.prem.portfolio.portfolio.entity.BlogTagMapping;
import com.prem.portfolio.portfolio.mapper.BlogTagMappingMapper;
import com.prem.portfolio.portfolio.repository.BlogRepository;
import com.prem.portfolio.portfolio.repository.BlogTagMappingRepository;
import com.prem.portfolio.portfolio.repository.BlogTagRepository;
import com.prem.portfolio.portfolio.service.BlogTagMappingService;

@Service
@Transactional
public class BlogTagMappingServiceImpl implements BlogTagMappingService {

    private final BlogTagMappingRepository mappingRepository;
    private final BlogRepository blogRepository;
    private final BlogTagRepository tagRepository;
    private final BlogTagMappingMapper mappingMapper;

    public BlogTagMappingServiceImpl(
            BlogTagMappingRepository mappingRepository,
            BlogRepository blogRepository,
            BlogTagRepository tagRepository,
            BlogTagMappingMapper mappingMapper) {

        this.mappingRepository = mappingRepository;
        this.blogRepository = blogRepository;
        this.tagRepository = tagRepository;
        this.mappingMapper = mappingMapper;
    }

    @Override
    public BlogTagMappingResponse addTag(
            Long blogId,
            BlogTagMappingRequest request) {

        validateActiveBlog(blogId);

        Long tagId = request.getBlogTagId();

        BlogTag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Blog tag not found: " + tagId));

        if (mappingRepository.existsByBlogIdAndBlogTagId(blogId, tagId)) {
            throw new IllegalArgumentException(
                    "This tag is already assigned to the blog");
        }

        BlogTagMapping mapping =
                mappingMapper.toEntity(blogId, tagId);

        BlogTagMapping savedMapping =
                mappingRepository.save(mapping);

        return mappingMapper.toResponse(savedMapping, tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogTagMappingResponse> getBlogTags(Long blogId) {

        validateActiveBlog(blogId);

        return mappingRepository.findByBlogId(blogId)
                .stream()
                .map(mapping -> {
                    BlogTag tag = tagRepository.findById(
                            mapping.getBlogTagId()
                    ).orElseThrow(() -> new IllegalArgumentException(
                            "Blog tag not found: "
                                    + mapping.getBlogTagId()));

                    return mappingMapper.toResponse(mapping, tag);
                })
                .toList();
    }

    @Override
    public void removeTag(Long blogId, Long blogTagId) {

        validateActiveBlog(blogId);

        if (!mappingRepository.existsByBlogIdAndBlogTagId(
                blogId, blogTagId)) {

            throw new IllegalArgumentException(
                    "Tag is not assigned to this blog");
        }

        mappingRepository.deleteByBlogIdAndBlogTagId(
                blogId, blogTagId);
    }

    private void validateActiveBlog(Long blogId) {

        blogRepository.findByBlogIdAndActiveTrue(blogId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active blog not found: " + blogId));
    }
}

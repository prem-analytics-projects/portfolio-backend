
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.BlogTagRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagResponse;
import com.prem.portfolio.portfolio.entity.BlogTag;
import com.prem.portfolio.portfolio.mapper.BlogTagMapper;
import com.prem.portfolio.portfolio.repository.BlogTagRepository;
import com.prem.portfolio.portfolio.service.BlogTagService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class BlogTagServiceImpl implements BlogTagService {

    private final BlogTagRepository tagRepository;
    private final BlogTagMapper tagMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public BlogTagServiceImpl(
            BlogTagRepository tagRepository,
            BlogTagMapper tagMapper) {

        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public BlogTagResponse createTag(BlogTagRequest request) {

        String tagName = normalizeName(request.getTagName());

        validateUniqueName(tagName, null);

        BlogTag tag = tagMapper.toEntity(request);
        tag.setTagName(tagName);

        return tagMapper.toResponse(tagRepository.save(tag));
    }

    @Override
    public BlogTagResponse updateTag(
            Long blogTagId,
            BlogTagRequest request) {

        BlogTag tag = tagRepository.findById(blogTagId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Blog tag not found"));

        String tagName = normalizeName(request.getTagName());

        validateUniqueName(tagName, blogTagId);

        tagMapper.updateEntity(tag, request);
        tag.setTagName(tagName);

        return tagMapper.toResponse(tagRepository.save(tag));
    }

    @Override
    @Transactional(readOnly = true)
    public BlogTagResponse getTagById(Long blogTagId) {

        BlogTag tag = tagRepository.findById(blogTagId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Blog tag not found"));

        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogTagResponse> getAllTags() {

        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteTag(Long blogTagId) {

        BlogTag tag = tagRepository.findById(blogTagId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Blog tag not found"));

        Number count = (Number) entityManager.createNativeQuery("""
                SELECT COUNT(*)
                FROM portfolio.blog_tag_mapping
                WHERE blog_tag_id = :tagId
                """)
                .setParameter("tagId", blogTagId)
                .getSingleResult();

        if (count.longValue() > 0) {
            throw new IllegalArgumentException(
                    "Cannot delete this tag because it is assigned to blogs");
        }

        tagRepository.delete(tag);
    }

    private String normalizeName(String tagName) {

        if (tagName == null || tagName.isBlank()) {
            throw new IllegalArgumentException("Tag name is required");
        }

        return tagName.trim();
    }

    private void validateUniqueName(
            String tagName,
            Long currentTagId) {

        tagRepository.findByTagNameIgnoreCase(tagName)
                .ifPresent(existingTag -> {

                    if (currentTagId == null
                            || !existingTag.getBlogTagId()
                                    .equals(currentTagId)) {

                        throw new IllegalArgumentException(
                                "Blog tag already exists: " + tagName);
                    }
                });
    }
}


package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.BlogCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryResponse;
import com.prem.portfolio.portfolio.entity.BlogCategory;
import com.prem.portfolio.portfolio.mapper.BlogCategoryMapper;
import com.prem.portfolio.portfolio.repository.BlogCategoryRepository;
import com.prem.portfolio.portfolio.service.BlogCategoryService;

@Service
@Transactional
public class BlogCategoryServiceImpl implements BlogCategoryService {

    private final BlogCategoryRepository categoryRepository;
    private final BlogCategoryMapper categoryMapper;

    public BlogCategoryServiceImpl(
            BlogCategoryRepository categoryRepository,
            BlogCategoryMapper categoryMapper) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public BlogCategoryResponse createCategory(
            BlogCategoryRequest request) {

        String categoryName = normalizeName(request.getCategoryName());

        validateUniqueName(categoryName, null);

        BlogCategory category = categoryMapper.toEntity(request);
        category.setCategoryName(categoryName);

        BlogCategory savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public BlogCategoryResponse updateCategory(
            Long blogCategoryId,
            BlogCategoryRequest request) {

        BlogCategory category = categoryRepository
                .findByBlogCategoryIdAndActiveTrue(blogCategoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active blog category not found"));

        String categoryName = normalizeName(request.getCategoryName());

        validateUniqueName(categoryName, blogCategoryId);

        categoryMapper.updateEntity(category, request);
        category.setCategoryName(categoryName);

        BlogCategory updatedCategory =
                categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public BlogCategoryResponse getCategoryById(Long blogCategoryId) {

        BlogCategory category = categoryRepository
                .findByBlogCategoryIdAndActiveTrue(blogCategoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active blog category not found"));

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogCategoryResponse> getCategories(Boolean active) {

        List<BlogCategory> categories = active == null
                ? categoryRepository.findAll()
                : categoryRepository.findByActive(active);

        return categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long blogCategoryId) {

        BlogCategory category = categoryRepository
                .findByBlogCategoryIdAndActiveTrue(blogCategoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active blog category not found"));

        category.setActive(false);

        categoryRepository.save(category);
    }

    private String normalizeName(String categoryName) {

        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException(
                    "Category name is required");
        }

        return categoryName.trim();
    }

    private void validateUniqueName(
            String categoryName,
            Long currentCategoryId) {

        categoryRepository.findByCategoryNameIgnoreCase(categoryName)
                .ifPresent(existingCategory -> {

                    if (currentCategoryId == null
                            || !existingCategory.getBlogCategoryId()
                                    .equals(currentCategoryId)) {

                        throw new IllegalArgumentException(
                                "Blog category already exists: "
                                        + categoryName);
                    }
                });
    }
}

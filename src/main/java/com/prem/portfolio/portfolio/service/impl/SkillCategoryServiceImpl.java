
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.portfolio.dto.request.SkillCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.SkillCategoryResponse;
import com.prem.portfolio.portfolio.entity.SkillCategory;
import com.prem.portfolio.common.exception.ResourceNotFoundException;
import com.prem.portfolio.portfolio.mapper.SkillCategoryMapper;
import com.prem.portfolio.portfolio.repository.SkillCategoryRepository;
import com.prem.portfolio.portfolio.service.SkillCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final SkillCategoryRepository categoryRepository;
    private final SkillCategoryMapper categoryMapper;

    public SkillCategoryServiceImpl(
            SkillCategoryRepository categoryRepository,
            SkillCategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public SkillCategoryResponse createCategory(SkillCategoryRequest request) {

        if (categoryRepository.existsByCategoryNameIgnoreCase(
                request.getCategoryName().trim())) {
            throw new IllegalArgumentException(
                    "A skill category with this name already exists"
            );
        }

        request.setCategoryName(request.getCategoryName().trim());

        SkillCategory category = categoryMapper.toEntity(request);

        SkillCategory savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public SkillCategoryResponse updateCategory(
            Long skillCategoryId,
            SkillCategoryRequest request
    ) {
        SkillCategory category = categoryRepository.findById(skillCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill category not found with ID: " + skillCategoryId
                ));

        String categoryName = request.getCategoryName().trim();

        categoryRepository.findByCategoryNameIgnoreCase(categoryName)
                .filter(existing -> !existing.getSkillCategoryId().equals(skillCategoryId))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "A skill category with this name already exists"
                    );
                });

        request.setCategoryName(categoryName);

        categoryMapper.updateEntity(category, request);

        SkillCategory updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public SkillCategoryResponse getCategoryById(Long skillCategoryId) {

        SkillCategory category = categoryRepository.findById(skillCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill category not found with ID: " + skillCategoryId
                ));

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillCategoryResponse> getAllCategories(Boolean active) {

        List<SkillCategory> categories;

        if (active == null) {
            categories = categoryRepository
                    .findAllByOrderByDisplayOrderAscCategoryNameAsc();
        } else {
            categories = categoryRepository
                    .findByActiveOrderByDisplayOrderAscCategoryNameAsc(active);
        }

        return categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long skillCategoryId) {

        SkillCategory category = categoryRepository.findById(skillCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill category not found with ID: " + skillCategoryId
                ));

        category.setActive(false);

        categoryRepository.save(category);
    }
}

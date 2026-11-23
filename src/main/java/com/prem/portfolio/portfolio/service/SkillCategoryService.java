
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.SkillCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.SkillCategoryResponse;

import java.util.List;

public interface SkillCategoryService {

    SkillCategoryResponse createCategory(SkillCategoryRequest request);

    SkillCategoryResponse updateCategory(
            Long skillCategoryId,
            SkillCategoryRequest request
    );

    SkillCategoryResponse getCategoryById(Long skillCategoryId);

    List<SkillCategoryResponse> getAllCategories(Boolean active);

    void deleteCategory(Long skillCategoryId);
}

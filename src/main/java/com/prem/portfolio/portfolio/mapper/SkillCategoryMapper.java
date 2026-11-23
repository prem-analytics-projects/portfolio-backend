
package com.prem.portfolio.portfolio.mapper;

import com.prem.portfolio.portfolio.dto.request.SkillCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.SkillCategoryResponse;
import com.prem.portfolio.portfolio.entity.SkillCategory;
import org.springframework.stereotype.Component;

@Component
public class SkillCategoryMapper {

    public SkillCategory toEntity(SkillCategoryRequest request) {
        SkillCategory category = new SkillCategory();

        category.setCategoryName(request.getCategoryName());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setActive(request.getActive());

        return category;
    }

    public void updateEntity(
            SkillCategory category,
            SkillCategoryRequest request
    ) {
        category.setCategoryName(request.getCategoryName());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setActive(request.getActive());
    }

    public SkillCategoryResponse toResponse(SkillCategory category) {
        SkillCategoryResponse response = new SkillCategoryResponse();

        response.setSkillCategoryId(category.getSkillCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setDisplayOrder(category.getDisplayOrder());
        response.setActive(category.getActive());

        return response;
    }
}

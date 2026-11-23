
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.BlogCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryResponse;
import com.prem.portfolio.portfolio.entity.BlogCategory;

@Component
public class BlogCategoryMapper {

    public BlogCategory toEntity(BlogCategoryRequest request) {

        BlogCategory category = new BlogCategory();

        category.setCategoryName(request.getCategoryName().trim());
        category.setDescription(request.getDescription());
        category.setActive(request.getActive());

        return category;
    }

    public void updateEntity(
            BlogCategory category,
            BlogCategoryRequest request) {

        category.setCategoryName(request.getCategoryName().trim());
        category.setDescription(request.getDescription());
        category.setActive(request.getActive());
    }

    public BlogCategoryResponse toResponse(BlogCategory category) {

        BlogCategoryResponse response = new BlogCategoryResponse();

        response.setBlogCategoryId(category.getBlogCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setActive(category.getActive());

        return response;
    }
}

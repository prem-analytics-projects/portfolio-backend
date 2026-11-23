
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.BlogCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryResponse;

public interface BlogCategoryService {

    BlogCategoryResponse createCategory(BlogCategoryRequest request);

    BlogCategoryResponse updateCategory(
            Long blogCategoryId,
            BlogCategoryRequest request
    );

    BlogCategoryResponse getCategoryById(Long blogCategoryId);

    List<BlogCategoryResponse> getCategories(Boolean active);

    void deleteCategory(Long blogCategoryId);
}

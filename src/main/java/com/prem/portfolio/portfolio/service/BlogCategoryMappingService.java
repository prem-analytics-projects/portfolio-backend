
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.BlogCategoryMappingRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryMappingResponse;

public interface BlogCategoryMappingService {

    BlogCategoryMappingResponse addCategory(
            Long blogId,
            BlogCategoryMappingRequest request
    );

    List<BlogCategoryMappingResponse> getBlogCategories(Long blogId);

    void removeCategory(Long blogId, Long blogCategoryId);
}

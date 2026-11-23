
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.response.BlogCategoryMappingResponse;
import com.prem.portfolio.portfolio.entity.BlogCategory;
import com.prem.portfolio.portfolio.entity.BlogCategoryMapping;

@Component
public class BlogCategoryMappingMapper {

    public BlogCategoryMapping toEntity(Long blogId, Long blogCategoryId) {

        BlogCategoryMapping mapping = new BlogCategoryMapping();

        mapping.setBlogId(blogId);
        mapping.setBlogCategoryId(blogCategoryId);

        return mapping;
    }

    public BlogCategoryMappingResponse toResponse(
            BlogCategoryMapping mapping,
            BlogCategory category) {

        BlogCategoryMappingResponse response =
                new BlogCategoryMappingResponse();

        response.setBlogId(mapping.getBlogId());
        response.setBlogCategoryId(mapping.getBlogCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setActive(category.getActive());

        return response;
    }
}

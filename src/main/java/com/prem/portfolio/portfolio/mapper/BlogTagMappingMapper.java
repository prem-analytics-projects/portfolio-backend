
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.response.BlogTagMappingResponse;
import com.prem.portfolio.portfolio.entity.BlogTag;
import com.prem.portfolio.portfolio.entity.BlogTagMapping;

@Component
public class BlogTagMappingMapper {

    public BlogTagMapping toEntity(Long blogId, Long blogTagId) {

        BlogTagMapping mapping = new BlogTagMapping();

        mapping.setBlogId(blogId);
        mapping.setBlogTagId(blogTagId);

        return mapping;
    }

    public BlogTagMappingResponse toResponse(
            BlogTagMapping mapping,
            BlogTag tag) {

        BlogTagMappingResponse response =
                new BlogTagMappingResponse();

        response.setBlogId(mapping.getBlogId());
        response.setBlogTagId(mapping.getBlogTagId());
        response.setTagName(tag.getTagName());

        return response;
    }
}

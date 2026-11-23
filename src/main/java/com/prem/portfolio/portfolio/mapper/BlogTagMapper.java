
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.BlogTagRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagResponse;
import com.prem.portfolio.portfolio.entity.BlogTag;

@Component
public class BlogTagMapper {

    public BlogTag toEntity(BlogTagRequest request) {

        BlogTag tag = new BlogTag();

        tag.setTagName(request.getTagName().trim());

        return tag;
    }

    public void updateEntity(BlogTag tag, BlogTagRequest request) {

        tag.setTagName(request.getTagName().trim());
    }

    public BlogTagResponse toResponse(BlogTag tag) {

        BlogTagResponse response = new BlogTagResponse();

        response.setBlogTagId(tag.getBlogTagId());
        response.setTagName(tag.getTagName());

        return response;
    }
}

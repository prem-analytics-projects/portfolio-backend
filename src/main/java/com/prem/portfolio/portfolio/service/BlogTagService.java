
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.BlogTagRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagResponse;

public interface BlogTagService {

    BlogTagResponse createTag(BlogTagRequest request);

    BlogTagResponse updateTag(Long blogTagId, BlogTagRequest request);

    BlogTagResponse getTagById(Long blogTagId);

    List<BlogTagResponse> getAllTags();

    void deleteTag(Long blogTagId);
}

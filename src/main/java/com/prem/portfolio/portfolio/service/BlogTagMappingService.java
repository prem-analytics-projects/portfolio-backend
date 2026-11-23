
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.BlogTagMappingRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagMappingResponse;

public interface BlogTagMappingService {

    BlogTagMappingResponse addTag(
            Long blogId,
            BlogTagMappingRequest request
    );

    List<BlogTagMappingResponse> getBlogTags(Long blogId);

    void removeTag(Long blogId, Long blogTagId);
}

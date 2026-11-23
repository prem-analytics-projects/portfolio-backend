
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.BlogRequest;
import com.prem.portfolio.portfolio.dto.response.BlogResponse;

public interface BlogService {

    BlogResponse createBlog(BlogRequest request);

    BlogResponse updateBlog(Long blogId, BlogRequest request);

    BlogResponse getBlogById(Long blogId);

    List<BlogResponse> getBlogs(
            Long profileId,
            Boolean active,
            String publicationStatus
    );

    void deleteBlog(Long blogId);
}


package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.BlogRequest;
import com.prem.portfolio.portfolio.dto.response.BlogResponse;
import com.prem.portfolio.portfolio.service.BlogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
            @Valid @RequestBody BlogRequest request) {

        BlogResponse response = blogService.createBlog(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>> getBlogs(
            @RequestParam(required = false) Long profileId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String publicationStatus) {

        return ResponseEntity.ok(
                blogService.getBlogs(profileId, active, publicationStatus)
        );
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogResponse> getBlogById(
            @PathVariable Long blogId) {

        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogRequest request) {

        return ResponseEntity.ok(
                blogService.updateBlog(blogId, request)
        );
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long blogId) {

        blogService.deleteBlog(blogId);

        return ResponseEntity.noContent().build();
    }
}


package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.BlogTagRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagResponse;
import com.prem.portfolio.portfolio.service.BlogTagService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/blog-tags")
public class BlogTagController {

    private final BlogTagService tagService;

    public BlogTagController(BlogTagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<BlogTagResponse> createTag(
            @Valid @RequestBody BlogTagRequest request) {

        BlogTagResponse response = tagService.createTag(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogTagResponse>> getAllTags() {

        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{blogTagId}")
    public ResponseEntity<BlogTagResponse> getTagById(
            @PathVariable Long blogTagId) {

        return ResponseEntity.ok(tagService.getTagById(blogTagId));
    }

    @PutMapping("/{blogTagId}")
    public ResponseEntity<BlogTagResponse> updateTag(
            @PathVariable Long blogTagId,
            @Valid @RequestBody BlogTagRequest request) {

        return ResponseEntity.ok(
                tagService.updateTag(blogTagId, request)
        );
    }

    @DeleteMapping("/{blogTagId}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long blogTagId) {

        tagService.deleteTag(blogTagId);

        return ResponseEntity.noContent().build();
    }
}


package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.prem.portfolio.portfolio.dto.request.BlogTagMappingRequest;
import com.prem.portfolio.portfolio.dto.response.BlogTagMappingResponse;
import com.prem.portfolio.portfolio.service.BlogTagMappingService;

@RestController
@RequestMapping("/api/v1/admin/blogs/{blogId}/tags")
public class BlogTagMappingController {

    private final BlogTagMappingService mappingService;

    public BlogTagMappingController(
            BlogTagMappingService mappingService) {
        this.mappingService = mappingService;
    }

    @PostMapping
    public ResponseEntity<BlogTagMappingResponse> addTag(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogTagMappingRequest request) {

        BlogTagMappingResponse response =
                mappingService.addTag(blogId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogTagMappingResponse>> getBlogTags(
            @PathVariable Long blogId) {

        return ResponseEntity.ok(
                mappingService.getBlogTags(blogId));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> removeTag(
            @PathVariable Long blogId,
            @PathVariable Long tagId) {

        mappingService.removeTag(blogId, tagId);

        return ResponseEntity.noContent().build();
    }
}


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

import com.prem.portfolio.portfolio.dto.request.BlogCategoryMappingRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryMappingResponse;
import com.prem.portfolio.portfolio.service.BlogCategoryMappingService;

@RestController
@RequestMapping("/api/v1/admin/blogs/{blogId}/categories")
public class BlogCategoryMappingController {

    private final BlogCategoryMappingService mappingService;

    public BlogCategoryMappingController(
            BlogCategoryMappingService mappingService) {
        this.mappingService = mappingService;
    }

    @PostMapping
    public ResponseEntity<BlogCategoryMappingResponse> addCategory(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogCategoryMappingRequest request) {

        BlogCategoryMappingResponse response =
                mappingService.addCategory(blogId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogCategoryMappingResponse>> getBlogCategories(
            @PathVariable Long blogId) {

        return ResponseEntity.ok(
                mappingService.getBlogCategories(blogId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> removeCategory(
            @PathVariable Long blogId,
            @PathVariable Long categoryId) {

        mappingService.removeCategory(blogId, categoryId);

        return ResponseEntity.noContent().build();
    }
}

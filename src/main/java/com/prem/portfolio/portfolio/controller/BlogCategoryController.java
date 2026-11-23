
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.BlogCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.BlogCategoryResponse;
import com.prem.portfolio.portfolio.service.BlogCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/blog-categories")
public class BlogCategoryController {

    private final BlogCategoryService categoryService;

    public BlogCategoryController(
            BlogCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<BlogCategoryResponse> createCategory(
            @Valid @RequestBody BlogCategoryRequest request) {

        BlogCategoryResponse response =
                categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogCategoryResponse>> getCategories(
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                categoryService.getCategories(active)
        );
    }

    @GetMapping("/{blogCategoryId}")
    public ResponseEntity<BlogCategoryResponse> getCategoryById(
            @PathVariable Long blogCategoryId) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(blogCategoryId)
        );
    }

    @PutMapping("/{blogCategoryId}")
    public ResponseEntity<BlogCategoryResponse> updateCategory(
            @PathVariable Long blogCategoryId,
            @Valid @RequestBody BlogCategoryRequest request) {

        return ResponseEntity.ok(
                categoryService.updateCategory(blogCategoryId, request)
        );
    }

    @DeleteMapping("/{blogCategoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long blogCategoryId) {

        categoryService.deleteCategory(blogCategoryId);

        return ResponseEntity.noContent().build();
    }
}

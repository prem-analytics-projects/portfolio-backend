
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.SkillCategoryRequest;
import com.prem.portfolio.portfolio.dto.response.SkillCategoryResponse;
import com.prem.portfolio.portfolio.service.SkillCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/skill-categories")
public class SkillCategoryController {

    private final SkillCategoryService categoryService;

    public SkillCategoryController(SkillCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<SkillCategoryResponse> createCategory(
            @Valid @RequestBody SkillCategoryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(request));
    }

    @GetMapping
    public ResponseEntity<List<SkillCategoryResponse>> getAllCategories(
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(categoryService.getAllCategories(active));
    }

    @GetMapping("/{skillCategoryId}")
    public ResponseEntity<SkillCategoryResponse> getCategoryById(
            @PathVariable Long skillCategoryId) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(skillCategoryId)
        );
    }

    @PutMapping("/{skillCategoryId}")
    public ResponseEntity<SkillCategoryResponse> updateCategory(
            @PathVariable Long skillCategoryId,
            @Valid @RequestBody SkillCategoryRequest request) {

        return ResponseEntity.ok(
                categoryService.updateCategory(skillCategoryId, request)
        );
    }

    @DeleteMapping("/{skillCategoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long skillCategoryId) {

        categoryService.deleteCategory(skillCategoryId);

        return ResponseEntity.noContent().build();
    }
}

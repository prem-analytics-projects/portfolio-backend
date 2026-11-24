
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.PublicSkillCategoryResponse;
import com.prem.portfolio.portfolio.dto.response.SkillCategoryResponse;
import com.prem.portfolio.portfolio.service.SkillCategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/skill-categories")
public class PublicSkillCategoryController {

    private final SkillCategoryService categoryService;

    public PublicSkillCategoryController(
            SkillCategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<PublicSkillCategoryResponse>> getPublicCategories() {

        // Force active=true so inactive categories are never exposed.
        List<SkillCategoryResponse> categories =
                categoryService.getAllCategories(true);

        List<PublicSkillCategoryResponse> response = categories.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicSkillCategoryResponse toPublicResponse(
            SkillCategoryResponse source
    ) {
        PublicSkillCategoryResponse response =
                new PublicSkillCategoryResponse();

        response.setSkillCategoryId(source.getSkillCategoryId());
        response.setCategoryName(source.getCategoryName());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}

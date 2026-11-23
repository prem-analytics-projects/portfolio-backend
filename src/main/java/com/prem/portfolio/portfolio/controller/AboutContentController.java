
package com.prem.portfolio.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.prem.portfolio.portfolio.dto.request.AboutContentRequest;
import com.prem.portfolio.portfolio.dto.response.AboutContentResponse;
import com.prem.portfolio.portfolio.service.AboutContentService;

@RestController
@RequestMapping("/api/v1/admin/about-content")
public class AboutContentController {

    private final AboutContentService aboutContentService;

    public AboutContentController(
            AboutContentService aboutContentService) {

        this.aboutContentService = aboutContentService;
    }

    // CREATE ABOUT CONTENT
    @PostMapping
    public ResponseEntity<AboutContentResponse> createAboutContent(
            @Valid @RequestBody AboutContentRequest request) {

        AboutContentResponse response =
                aboutContentService.createAboutContent(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL ABOUT CONTENT WITH OPTIONAL FILTERS
    @GetMapping
    public ResponseEntity<List<AboutContentResponse>> getAllAboutContent(
            @RequestParam(required = false) Long profileId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                aboutContentService.getAllAboutContent(profileId, active)
        );
    }

    // GET ABOUT CONTENT BY ID
    @GetMapping("/{aboutId}")
    public ResponseEntity<AboutContentResponse> getAboutContentById(
            @PathVariable Long aboutId) {

        return ResponseEntity.ok(
                aboutContentService.getAboutContentById(aboutId)
        );
    }

    // UPDATE ABOUT CONTENT
    @PutMapping("/{aboutId}")
    public ResponseEntity<AboutContentResponse> updateAboutContent(
            @PathVariable Long aboutId,
            @Valid @RequestBody AboutContentRequest request) {

        return ResponseEntity.ok(
                aboutContentService.updateAboutContent(aboutId, request)
        );
    }

    // SOFT DELETE ABOUT CONTENT
    @DeleteMapping("/{aboutId}")
    public ResponseEntity<Void> deleteAboutContent(
            @PathVariable Long aboutId) {

        aboutContentService.deleteAboutContent(aboutId);

        return ResponseEntity.noContent().build();
    }
}

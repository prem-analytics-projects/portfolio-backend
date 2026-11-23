
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.LanguageRequest;
import com.prem.portfolio.portfolio.dto.response.LanguageResponse;
import com.prem.portfolio.portfolio.service.LanguageService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity<LanguageResponse> createLanguage(
            @Valid @RequestBody LanguageRequest request) {

        LanguageResponse response =
                languageService.createLanguage(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getLanguages(
            @RequestParam Long profileId,
            @RequestParam(required = false) Boolean active) {

        return ResponseEntity.ok(
                languageService.getLanguages(profileId, active)
        );
    }

    @GetMapping("/{languageId}")
    public ResponseEntity<LanguageResponse> getLanguageById(
            @PathVariable Long languageId) {

        return ResponseEntity.ok(
                languageService.getLanguageById(languageId)
        );
    }

    @PutMapping("/{languageId}")
    public ResponseEntity<LanguageResponse> updateLanguage(
            @PathVariable Long languageId,
            @Valid @RequestBody LanguageRequest request) {

        return ResponseEntity.ok(
                languageService.updateLanguage(languageId, request)
        );
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity<Void> deleteLanguage(
            @PathVariable Long languageId) {

        languageService.deleteLanguage(languageId);

        return ResponseEntity.noContent().build();
    }
}


package com.prem.portfolio.portfolio.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.request.TechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.TechnologyResponse;
import com.prem.portfolio.portfolio.service.TechnologyService;

@RestController
@RequestMapping("/api/v1/admin/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping
    public ResponseEntity<TechnologyResponse> createTechnology(
            @Valid @RequestBody TechnologyRequest request
    ) {

        TechnologyResponse response =
                technologyService.createTechnology(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies() {

        return ResponseEntity.ok(
                technologyService.getAllTechnologies()
        );
    }

    @GetMapping("/{technologyId}")
    public ResponseEntity<TechnologyResponse> getTechnologyById(
            @PathVariable Long technologyId
    ) {

        return ResponseEntity.ok(
                technologyService.getTechnologyById(technologyId)
        );
    }

    @PutMapping("/{technologyId}")
    public ResponseEntity<TechnologyResponse> updateTechnology(
            @PathVariable Long technologyId,
            @Valid @RequestBody TechnologyRequest request
    ) {

        return ResponseEntity.ok(
                technologyService.updateTechnology(technologyId, request)
        );
    }

    @DeleteMapping("/{technologyId}")
    public ResponseEntity<Void> deleteTechnology(
            @PathVariable Long technologyId
    ) {

        technologyService.deleteTechnology(technologyId);

        return ResponseEntity.noContent().build();
    }
}

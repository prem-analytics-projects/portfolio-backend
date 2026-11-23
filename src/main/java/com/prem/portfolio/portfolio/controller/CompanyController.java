
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.request.CompanyRequest;
import com.prem.portfolio.portfolio.dto.response.CompanyResponse;
import com.prem.portfolio.portfolio.service.CompanyService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest request) {

        CompanyResponse response = companyService.createCompany(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getCompanies(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String search) {

        return ResponseEntity.ok(
                companyService.getCompanies(active, search)
        );
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                companyService.getCompanyById(companyId)
        );
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyRequest request) {

        return ResponseEntity.ok(
                companyService.updateCompany(companyId, request)
        );
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long companyId) {

        companyService.deleteCompany(companyId);

        return ResponseEntity.noContent().build();
    }
}

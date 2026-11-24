
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.portfolio.dto.response.CompanyResponse;
import com.prem.portfolio.portfolio.dto.response.PublicCompanyResponse;
import com.prem.portfolio.portfolio.service.CompanyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/companies")
public class PublicCompanyController {

    private final CompanyService companyService;

    public PublicCompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<PublicCompanyResponse>> getPublicCompanies(
            @RequestParam(required = false) String search
    ) {

        // Force active=true so inactive companies are never exposed.
        List<CompanyResponse> companies =
                companyService.getCompanies(true, search);

        List<PublicCompanyResponse> response = companies.stream()
                .map(this::toPublicResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private PublicCompanyResponse toPublicResponse(
            CompanyResponse source
    ) {

        PublicCompanyResponse response = new PublicCompanyResponse();

        response.setCompanyId(source.getCompanyId());
        response.setCompanyName(source.getCompanyName());
        response.setWebsite(source.getWebsite());
        response.setLogoUrl(source.getLogoUrl());
        response.setLocation(source.getLocation());
        response.setDescription(source.getDescription());

        return response;
    }
}

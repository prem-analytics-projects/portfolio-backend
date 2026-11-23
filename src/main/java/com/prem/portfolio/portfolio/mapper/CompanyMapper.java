
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.request.CompanyRequest;
import com.prem.portfolio.portfolio.dto.response.CompanyResponse;
import com.prem.portfolio.portfolio.entity.Company;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyRequest request) {
        Company company = new Company();
        updateEntity(company, request);
        return company;
    }

    public void updateEntity(Company company, CompanyRequest request) {
        company.setCompanyName(request.getCompanyName());
        company.setWebsite(request.getWebsite());
        company.setLogoMediaId(request.getLogoMediaId());
        company.setLocation(request.getLocation());
        company.setDescription(request.getDescription());
        company.setActive(request.getActive() != null ? request.getActive() : true);
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse response = new CompanyResponse();

        response.setCompanyId(company.getCompanyId());
        response.setCompanyName(company.getCompanyName());
        response.setWebsite(company.getWebsite());
        response.setLogoMediaId(company.getLogoMediaId());
        response.setLocation(company.getLocation());
        response.setDescription(company.getDescription());
        response.setActive(company.getActive());
        response.setCreatedAt(company.getCreatedAt());
        response.setUpdatedAt(company.getUpdatedAt());
        response.setCreatedBy(company.getCreatedBy());
        response.setUpdatedBy(company.getUpdatedBy());

        return response;
    }
}

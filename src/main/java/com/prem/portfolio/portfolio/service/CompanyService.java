
package com.prem.portfolio.portfolio.service;

import com.prem.portfolio.portfolio.dto.request.CompanyRequest;
import com.prem.portfolio.portfolio.dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(CompanyRequest request);

    CompanyResponse updateCompany(Long companyId, CompanyRequest request);

    CompanyResponse getCompanyById(Long companyId);

    List<CompanyResponse> getCompanies(Boolean active, String search);

    void deleteCompany(Long companyId);
}

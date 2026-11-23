
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.request.CompanyRequest;
import com.prem.portfolio.portfolio.dto.response.CompanyResponse;
import com.prem.portfolio.portfolio.entity.Company;
import com.prem.portfolio.portfolio.mapper.CompanyMapper;
import com.prem.portfolio.portfolio.repository.CompanyRepository;
import com.prem.portfolio.portfolio.service.CompanyService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MediaRepository mediaRepository;
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(
            CompanyRepository companyRepository,
            MediaRepository mediaRepository,
            CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.mediaRepository = mediaRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {

        validateDuplicateCompanyName(request.getCompanyName(), null);
        validateLogo(request.getLogoMediaId());

        Company company = companyMapper.toEntity(request);

        Company savedCompany = companyRepository.save(company);

        return buildResponse(savedCompany);
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, CompanyRequest request) {

        Company company = getActiveCompany(companyId);

        validateDuplicateCompanyName(
                request.getCompanyName(),
                companyId
        );

        validateLogo(request.getLogoMediaId());

        companyMapper.updateEntity(company, request);

        Company updatedCompany = companyRepository.save(company);

        return buildResponse(updatedCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Long companyId) {

        Company company = getActiveCompany(companyId);

        return buildResponse(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getCompanies(Boolean active, String search) {

        String searchValue = search == null ? "" : search.trim()
                .toLowerCase(Locale.ROOT);

        return companyRepository.findAll()
                .stream()
                .filter(company ->
                        active == null || active.equals(company.getActive()))
                .filter(company ->
                        searchValue.isEmpty()
                                || company.getCompanyName()
                                        .toLowerCase(Locale.ROOT)
                                        .contains(searchValue))
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void deleteCompany(Long companyId) {

        Company company = getActiveCompany(companyId);

        company.setActive(false);

        companyRepository.save(company);
    }

    private Company getActiveCompany(Long companyId) {

        return companyRepository.findByCompanyIdAndActiveTrue(companyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active company not found with ID: " + companyId
                        ));
    }

    private void validateDuplicateCompanyName(
            String companyName,
            Long currentCompanyId) {

        boolean duplicateExists = companyRepository
                .existsByCompanyNameIgnoreCaseAndActiveTrue(companyName);

        if (!duplicateExists) {
            return;
        }

        if (currentCompanyId != null) {
            Company existingCompany = companyRepository
                    .findAll()
                    .stream()
                    .filter(company ->
                            Boolean.TRUE.equals(company.getActive())
                                    && company.getCompanyName()
                                            .equalsIgnoreCase(companyName))
                    .findFirst()
                    .orElse(null);

            if (existingCompany != null
                    && existingCompany.getCompanyId().equals(currentCompanyId)) {
                return;
            }
        }

        throw new IllegalArgumentException(
                "An active company with this name already exists."
        );
    }

    private void validateLogo(Long logoMediaId) {

        if (logoMediaId == null) {
            return;
        }

        MediaAsset media = mediaRepository.findById(logoMediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Company logo media not found."
                        ));

        if (!Boolean.TRUE.equals(media.getIsActive())) {
            throw new IllegalArgumentException(
                    "Company logo media is inactive."
            );
        }

        if (!"IMAGE".equalsIgnoreCase(media.getFileType())) {
            throw new IllegalArgumentException(
                    "Company logo must be an IMAGE file."
            );
        }
    }

    private CompanyResponse buildResponse(Company company) {

        CompanyResponse response = companyMapper.toResponse(company);

        if (company.getLogoMediaId() != null) {

            mediaRepository.findById(company.getLogoMediaId())
                    .filter(media -> Boolean.TRUE.equals(media.getIsActive()))
                    .ifPresent(media ->
                            response.setLogoUrl(media.getFileUrl())
                    );
        }

        return response;
    }
}


package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByActive(Boolean active);

    Optional<Company> findByCompanyIdAndActiveTrue(Long companyId);

    boolean existsByCompanyNameIgnoreCaseAndActiveTrue(String companyName);
}

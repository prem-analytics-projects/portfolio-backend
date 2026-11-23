
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.Technology;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnologyRepository
        extends JpaRepository<Technology, Long> {

    Optional<Technology> findByTechnologyNameIgnoreCase(
            String technologyName
    );

    boolean existsByTechnologyNameIgnoreCase(
            String technologyName
    );
}

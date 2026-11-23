
package com.prem.portfolio.portfolio.service;

import java.util.List;

import com.prem.portfolio.portfolio.dto.request.TechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.TechnologyResponse;

public interface TechnologyService {

    TechnologyResponse createTechnology(TechnologyRequest request);

    TechnologyResponse updateTechnology(
            Long technologyId,
            TechnologyRequest request
    );

    TechnologyResponse getTechnologyById(Long technologyId);

    List<TechnologyResponse> getAllTechnologies();

    void deleteTechnology(Long technologyId);
}

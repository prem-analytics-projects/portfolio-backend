
package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.entity.Technology;
import com.prem.portfolio.portfolio.dto.request.TechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.TechnologyResponse;

@Component
public class TechnologyMapper {

    public Technology toEntity(TechnologyRequest request) {

        Technology technology = new Technology();

        technology.setTechnologyName(
                request.getTechnologyName().trim()
        );

        technology.setTechnologyType(
                request.getTechnologyType() == null
                        ? null
                        : request.getTechnologyType().trim()
        );

        return technology;
    }

    public void updateEntity(
            Technology technology,
            TechnologyRequest request
    ) {

        technology.setTechnologyName(
                request.getTechnologyName().trim()
        );

        technology.setTechnologyType(
                request.getTechnologyType() == null
                        ? null
                        : request.getTechnologyType().trim()
        );
    }

    public TechnologyResponse toResponse(Technology technology) {

        TechnologyResponse response = new TechnologyResponse();

        response.setTechnologyId(technology.getTechnologyId());
        response.setTechnologyName(technology.getTechnologyName());
        response.setTechnologyType(technology.getTechnologyType());
        response.setCreatedAt(technology.getCreatedAt());

        return response;
    }
}

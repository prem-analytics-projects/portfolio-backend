
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProjectTechnologyRequest {

    @NotNull(message = "Technology ID is required")
    @Positive(message = "Technology ID must be positive")
    private Long technologyId;

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }
}

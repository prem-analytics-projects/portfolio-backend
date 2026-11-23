
package com.prem.portfolio.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TechnologyRequest {

    @NotBlank(message = "Technology name is required")
    @Size(max = 100, message = "Technology name cannot exceed 100 characters")
    private String technologyName;

    @Size(max = 50, message = "Technology type cannot exceed 50 characters")
    private String technologyType;

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public String getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(String technologyType) {
        this.technologyType = technologyType;
    }
}

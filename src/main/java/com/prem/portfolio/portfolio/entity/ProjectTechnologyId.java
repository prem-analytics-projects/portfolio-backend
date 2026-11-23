
package com.prem.portfolio.portfolio.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProjectTechnologyId implements Serializable {

    private Long projectId;
    private Long technologyId;

    public ProjectTechnologyId() {
    }

    public ProjectTechnologyId(Long projectId, Long technologyId) {
        this.projectId = projectId;
        this.technologyId = technologyId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ProjectTechnologyId that)) {
            return false;
        }

        return Objects.equals(projectId, that.projectId)
                && Objects.equals(technologyId, that.technologyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, technologyId);
    }
}

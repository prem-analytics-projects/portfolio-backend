
package com.prem.portfolio.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "project_technologies",
    schema = "portfolio"
)
@IdClass(ProjectTechnologyId.class)
public class ProjectTechnology {

    @Id
    @Column(name = "project_id")
    private Long projectId;

    @Id
    @Column(name = "technology_id")
    private Long technologyId;

    public ProjectTechnology() {
    }

    public ProjectTechnology(Long projectId, Long technologyId) {
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
}

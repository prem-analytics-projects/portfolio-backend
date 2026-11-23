
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.ProjectTechnology;
import com.prem.portfolio.portfolio.entity.ProjectTechnologyId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTechnologyRepository
        extends JpaRepository<ProjectTechnology, ProjectTechnologyId> {

    List<ProjectTechnology> findByProjectId(Long projectId);

    boolean existsByProjectIdAndTechnologyId(
            Long projectId,
            Long technologyId
    );

    boolean existsByTechnologyId(Long technologyId);

    void deleteByProjectIdAndTechnologyId(
            Long projectId,
            Long technologyId
    );

    void deleteByProjectId(Long projectId);
}


package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.ProjectTechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectTechnologyResponse;
import com.prem.portfolio.portfolio.entity.Project;
import com.prem.portfolio.portfolio.entity.ProjectTechnology;
import com.prem.portfolio.portfolio.entity.Technology;
import com.prem.portfolio.portfolio.mapper.ProjectTechnologyMapper;
import com.prem.portfolio.portfolio.repository.ProjectRepository;
import com.prem.portfolio.portfolio.repository.ProjectTechnologyRepository;
import com.prem.portfolio.portfolio.repository.TechnologyRepository;
import com.prem.portfolio.portfolio.service.ProjectTechnologyService;

@Service
@Transactional
public class ProjectTechnologyServiceImpl
        implements ProjectTechnologyService {

    private final ProjectTechnologyRepository projectTechnologyRepository;
    private final ProjectRepository projectRepository;
    private final TechnologyRepository technologyRepository;
    private final ProjectTechnologyMapper projectTechnologyMapper;

    public ProjectTechnologyServiceImpl(
            ProjectTechnologyRepository projectTechnologyRepository,
            ProjectRepository projectRepository,
            TechnologyRepository technologyRepository,
            ProjectTechnologyMapper projectTechnologyMapper
    ) {
        this.projectTechnologyRepository = projectTechnologyRepository;
        this.projectRepository = projectRepository;
        this.technologyRepository = technologyRepository;
        this.projectTechnologyMapper = projectTechnologyMapper;
    }

    @Override
    public ProjectTechnologyResponse addTechnology(
            Long projectId,
            ProjectTechnologyRequest request
    ) {

        validateActiveProject(projectId);

        Technology technology = technologyRepository.findById(
                request.getTechnologyId()
        ).orElseThrow(() ->
                new IllegalArgumentException("Technology not found")
        );

        if (projectTechnologyRepository.existsByProjectIdAndTechnologyId(
                projectId,
                request.getTechnologyId()
        )) {
            throw new IllegalArgumentException(
                    "Technology is already assigned to this project"
            );
        }

        ProjectTechnology projectTechnology =
                projectTechnologyMapper.toEntity(
                        projectId,
                        request.getTechnologyId()
                );

        ProjectTechnology saved =
                projectTechnologyRepository.save(projectTechnology);

        return projectTechnologyMapper.toResponse(saved, technology);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectTechnologyResponse> getProjectTechnologies(
            Long projectId
    ) {

        validateActiveProject(projectId);

        return projectTechnologyRepository.findByProjectId(projectId)
                .stream()
                .map(projectTechnology -> {

                    Technology technology = technologyRepository.findById(
                            projectTechnology.getTechnologyId()
                    ).orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Technology not found: "
                                            + projectTechnology.getTechnologyId()
                            )
                    );

                    return projectTechnologyMapper.toResponse(
                            projectTechnology,
                            technology
                    );
                })
                .toList();
    }

    @Override
    public void removeTechnology(
            Long projectId,
            Long technologyId
    ) {

        validateActiveProject(projectId);

        if (!projectTechnologyRepository.existsByProjectIdAndTechnologyId(
                projectId,
                technologyId
        )) {
            throw new IllegalArgumentException(
                    "Technology is not assigned to this project"
            );
        }

        projectTechnologyRepository.deleteByProjectIdAndTechnologyId(
                projectId,
                technologyId
        );
    }

    private Project validateActiveProject(Long projectId) {

        return projectRepository.findByProjectIdAndActiveTrue(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active project not found"
                        )
                );
    }
}

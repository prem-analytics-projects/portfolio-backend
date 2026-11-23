
package com.prem.portfolio.portfolio.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.request.TechnologyRequest;
import com.prem.portfolio.portfolio.dto.response.TechnologyResponse;
import com.prem.portfolio.portfolio.entity.Technology;
import com.prem.portfolio.portfolio.mapper.TechnologyMapper;
import com.prem.portfolio.portfolio.repository.TechnologyRepository;
import com.prem.portfolio.portfolio.service.TechnologyService;
import com.prem.portfolio.portfolio.repository.ProjectTechnologyRepository;

@Service
@Transactional
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;
    private final ProjectTechnologyRepository projectTechnologyRepository;
    public TechnologyServiceImpl(
            TechnologyRepository technologyRepository,
            TechnologyMapper technologyMapper,
            ProjectTechnologyRepository projectTechnologyRepository
    ) {
        this.technologyRepository = technologyRepository;
        this.technologyMapper = technologyMapper;
        this.projectTechnologyRepository = projectTechnologyRepository;
    }

    @Override
    public TechnologyResponse createTechnology(TechnologyRequest request) {

        String technologyName = request.getTechnologyName().trim();

        validateDuplicateName(technologyName, null);

        Technology technology = technologyMapper.toEntity(request);

        Technology savedTechnology =
                technologyRepository.save(technology);

        return technologyMapper.toResponse(savedTechnology);
    }

    @Override
    public TechnologyResponse updateTechnology(
            Long technologyId,
            TechnologyRequest request
    ) {

        Technology technology = getTechnologyEntity(technologyId);

        String technologyName = request.getTechnologyName().trim();

        validateDuplicateName(technologyName, technologyId);

        technologyMapper.updateEntity(technology, request);

        Technology updatedTechnology =
                technologyRepository.save(technology);

        return technologyMapper.toResponse(updatedTechnology);
    }

    @Override
    @Transactional(readOnly = true)
    public TechnologyResponse getTechnologyById(Long technologyId) {

        Technology technology = getTechnologyEntity(technologyId);

        return technologyMapper.toResponse(technology);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnologyResponse> getAllTechnologies() {

        return technologyRepository.findAll()
                .stream()
                .map(technologyMapper::toResponse)
                .toList();
    }

    @Override
public void deleteTechnology(Long technologyId) {

    Technology technology = technologyRepository.findById(technologyId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Technology not found")
            );

    if (projectTechnologyRepository.existsByTechnologyId(technologyId)) {
        throw new IllegalArgumentException(
                "Cannot delete this technology because it is assigned to one or more projects. "
                + "Remove it from those projects first."
        );
    }

    technologyRepository.delete(technology);
}

    private Technology getTechnologyEntity(Long technologyId) {

        return technologyRepository.findById(technologyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Technology not found with ID: " + technologyId
                        )
                );
    }

    private void validateDuplicateName(
            String technologyName,
            Long currentTechnologyId
    ) {

        technologyRepository
                .findByTechnologyNameIgnoreCase(technologyName)
                .ifPresent(existingTechnology -> {

                    if (!existingTechnology.getTechnologyId()
                            .equals(currentTechnologyId)) {

                        throw new IllegalArgumentException(
                                "Technology already exists: " + technologyName
                        );
                    }
                });
    }
}

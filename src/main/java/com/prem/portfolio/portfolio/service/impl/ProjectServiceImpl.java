
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.request.ProjectRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectResponse;
import com.prem.portfolio.portfolio.entity.Project;
import com.prem.portfolio.portfolio.mapper.ProjectMapper;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.repository.ProjectRepository;
import com.prem.portfolio.portfolio.mapper.ProjectMediaMapper;
import com.prem.portfolio.portfolio.repository.ProjectMediaRepository;
import com.prem.portfolio.portfolio.service.ProjectService;
import com.prem.portfolio.portfolio.dto.response.ProjectImpactMetricResponse;
import com.prem.portfolio.portfolio.mapper.ProjectImpactMetricMapper;
import com.prem.portfolio.portfolio.repository.ProjectImpactMetricRepository;
import com.prem.portfolio.portfolio.repository.ProjectTechnologyRepository;

import com.prem.portfolio.portfolio.repository.TechnologyRepository;
import com.prem.portfolio.portfolio.mapper.ProjectTechnologyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final MediaRepository mediaRepository;
    private final ProjectMapper projectMapper;
    private final ProjectImpactMetricRepository impactMetricRepository;
private final ProjectImpactMetricMapper impactMetricMapper;
private final ProjectMediaRepository projectMediaRepository;
private final ProjectMediaMapper projectMediaMapper;
private final ProjectTechnologyRepository projectTechnologyRepository;
private final ProjectTechnologyMapper projectTechnologyMapper;
private final TechnologyRepository technologyRepository;
    public ProjectServiceImpl(
        ProjectRepository projectRepository,
        ProfileRepository profileRepository,
        MediaRepository mediaRepository,
        ProjectMapper projectMapper,
        ProjectImpactMetricRepository impactMetricRepository,
        ProjectImpactMetricMapper impactMetricMapper,
        ProjectMediaRepository projectMediaRepository,
ProjectMediaMapper projectMediaMapper,
ProjectTechnologyRepository projectTechnologyRepository,
ProjectTechnologyMapper projectTechnologyMapper,
TechnologyRepository technologyRepository
) {
    this.projectRepository = projectRepository;
    this.profileRepository = profileRepository;
    this.mediaRepository = mediaRepository;
    this.projectMapper = projectMapper;
    this.impactMetricRepository = impactMetricRepository;
    this.impactMetricMapper = impactMetricMapper;
    this.projectMediaRepository = projectMediaRepository;
this.projectMediaMapper = projectMediaMapper;
this.projectTechnologyRepository = projectTechnologyRepository;
this.projectTechnologyMapper = projectTechnologyMapper;
this.technologyRepository = technologyRepository;
}

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        validateProfile(request.getProfileId());
        validateDates(request);
        validateThumbnail(request.getThumbnailMediaId());

        validateDuplicateTitle(
                request.getProfileId(),
                request.getTitle(),
                null
        );

        Project project = projectMapper.toEntity(request);

        Project savedProject = projectRepository.save(project);

        return buildResponse(savedProject);
    }

    @Override
    public ProjectResponse updateProject(
            Long projectId,
            ProjectRequest request
    ) {

        Project project = projectRepository
                .findByProjectIdAndActiveTrue(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Active project not found")
                );

        validateProfile(request.getProfileId());
        validateDates(request);
        validateThumbnail(request.getThumbnailMediaId());

        validateDuplicateTitle(
                request.getProfileId(),
                request.getTitle(),
                projectId
        );

        projectMapper.updateEntity(project, request);

        Project updatedProject = projectRepository.save(project);

        return buildResponse(updatedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long projectId) {

        Project project = projectRepository
                .findByProjectIdAndActiveTrue(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Active project not found")
                );

        return buildResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects(
            Long profileId,
            Boolean active,
            Boolean featured
    ) {

        validateProfileExists(profileId);

        return projectRepository
                .findProjects(profileId, active, featured)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void deleteProject(Long projectId) {

        Project project = projectRepository
                .findByProjectIdAndActiveTrue(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Active project not found")
                );

        project.setActive(false);

        projectRepository.save(project);
    }

    private void validateProfile(Long profileId) {

        var profile = profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Profile not found")
                );

        if (!Boolean.TRUE.equals(profile.getActive())) {
            throw new IllegalArgumentException("Profile is inactive");
        }
    }

    private void validateProfileExists(Long profileId) {

        if (!profileRepository.existsById(profileId)) {
            throw new IllegalArgumentException("Profile not found");
        }
    }

    private void validateDates(ProjectRequest request) {

        if (request.getStartDate() != null
                && request.getEndDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {

            throw new IllegalArgumentException(
                    "Project end date cannot be before start date"
            );
        }
    }

    private void validateThumbnail(Long thumbnailMediaId) {

        if (thumbnailMediaId == null) {
            return;
        }

        MediaAsset media = mediaRepository.findById(thumbnailMediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Thumbnail media not found")
                );

        if (!Boolean.TRUE.equals(media.getIsActive())) {
            throw new IllegalArgumentException("Thumbnail media is inactive");
        }

        if (!"IMAGE".equalsIgnoreCase(media.getFileType())) {
            throw new IllegalArgumentException(
                    "Project thumbnail must be an image"
            );
        }
    }

    private void validateDuplicateTitle(
            Long profileId,
            String title,
            Long currentProjectId
    ) {

        boolean duplicate = projectRepository
                .findProjects(profileId, true, null)
                .stream()
                .anyMatch(project ->
                        !project.getProjectId().equals(currentProjectId)
                        && project.getTitle().equalsIgnoreCase(title.trim())
                );

        if (duplicate) {
            throw new IllegalArgumentException(
                    "An active project with this title already exists for this profile"
            );
        }
    }

    private ProjectResponse buildResponse(Project project) {

    ProjectResponse response = projectMapper.toResponse(project);

    // Populate thumbnail URL
    if (project.getThumbnailMediaId() != null) {

        mediaRepository.findById(project.getThumbnailMediaId())
                .filter(media ->
                        Boolean.TRUE.equals(media.getIsActive())
                )
                .ifPresent(media ->
                        response.setThumbnailUrl(media.getFileUrl())
                );
    }

    // Populate active impact metrics
    List<ProjectImpactMetricResponse> impactMetrics =
            impactMetricRepository
                    .findMetrics(project.getProjectId(), true)
                    .stream()
                    .map(impactMetricMapper::toResponse)
                    .toList();

    response.setImpactMetrics(impactMetrics);

response.setProjectMedia(
    projectMediaRepository.findProjectMedia(
        project.getProjectId(),
        true
    )
    .stream()
    .map(projectMediaMapper::toResponse)
    .map(mediaResponse -> {
        mediaRepository.findById(mediaResponse.getMediaId())
            .ifPresent(media -> {
                mediaResponse.setFileName(media.getFileName());
                mediaResponse.setFileType(media.getFileType());
                mediaResponse.setMimeType(media.getMimeType());
                mediaResponse.setMediaUrl(media.getFileUrl());
            });

        return mediaResponse;
    })
    .toList()
);
response.setTechnologies(
    projectTechnologyRepository.findByProjectId(
        project.getProjectId()
    )
    .stream()
    .map(projectTechnology -> {
        var technology = technologyRepository.findById(
            projectTechnology.getTechnologyId()
        ).orElse(null);

        if (technology == null) {
            return null;
        }

        return projectTechnologyMapper.toResponse(
            projectTechnology,
            technology
        );
    })
    .filter(java.util.Objects::nonNull)
    .toList()
);

return response;
}
}

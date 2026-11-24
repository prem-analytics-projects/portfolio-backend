
package com.prem.portfolio.portfolio.controller;

import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.response.ProjectImpactMetricResponse;
import com.prem.portfolio.portfolio.dto.response.ProjectMediaResponse;
import com.prem.portfolio.portfolio.dto.response.ProjectResponse;
import com.prem.portfolio.portfolio.dto.response.PublicProjectResponse;
import com.prem.portfolio.portfolio.repository.ProfileRepository;
import com.prem.portfolio.portfolio.service.ProjectService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/projects")
public class PublicProjectController {

    private final ProjectService projectService;
    private final ProfileRepository profileRepository;
    private final MediaRepository mediaRepository;

    public PublicProjectController(
            ProjectService projectService,
            ProfileRepository profileRepository,
            MediaRepository mediaRepository
    ) {
        this.projectService = projectService;
        this.profileRepository = profileRepository;
        this.mediaRepository = mediaRepository;
    }

    @GetMapping
    public List<PublicProjectResponse> getPublicProjects(
            @RequestParam Long profileId
    ) {

        if (profileId == null || profileId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A valid profileId is required"
            );
        }

        profileRepository.findByProfileIdAndActiveTrue(profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Active profile not found"
                ));

        List<ProjectResponse> projects =
                projectService.getProjects(profileId, true, null);

        return projects.stream()
                .map(this::toPublicResponse)
                .toList();
    }

    private PublicProjectResponse toPublicResponse(
            ProjectResponse source
    ) {

        PublicProjectResponse response =
                new PublicProjectResponse();

        response.setProjectId(source.getProjectId());
        response.setProfileId(source.getProfileId());
        response.setTitle(source.getTitle());
        response.setDescription(source.getDescription());
        response.setProblemStatement(source.getProblemStatement());
        response.setSolutionSummary(source.getSolutionSummary());
        response.setImpactSummary(source.getImpactSummary());
        response.setStartDate(source.getStartDate());
        response.setEndDate(source.getEndDate());
        response.setProjectUrl(source.getProjectUrl());
        response.setGithubUrl(source.getGithubUrl());
        response.setThumbnailUrl(source.getThumbnailUrl());
        response.setFeatured(source.getFeatured());
        response.setDisplayOrder(source.getDisplayOrder());

        // Include active impact metrics only.
        List<PublicProjectResponse.PublicProjectMetric> metrics =
                source.getImpactMetrics() == null
                        ? List.of()
                        : source.getImpactMetrics().stream()
                                .filter(metric ->
                                        Boolean.TRUE.equals(metric.getActive())
                                )
                                .map(this::toPublicMetric)
                                .toList();

        response.setImpactMetrics(metrics);

        // Include project media only when both the
        // project-media record and its actual media asset are active.
        List<PublicProjectResponse.PublicProjectMedia> media =
                source.getProjectMedia() == null
                        ? List.of()
                        : source.getProjectMedia().stream()
                                .filter(item ->
                                        Boolean.TRUE.equals(item.getActive())
                                )
                                .filter(this::isMediaActive)
                                .map(this::toPublicMedia)
                                .toList();

        response.setProjectMedia(media);

        // Technology records have no active flag in the current schema.
        response.setTechnologies(
                source.getTechnologies() == null
                        ? List.of()
                        : source.getTechnologies().stream()
                                .map(item -> {
                                    PublicProjectResponse.PublicProjectTechnology technology =
                                            new PublicProjectResponse.PublicProjectTechnology();

                                    technology.setTechnologyName(
                                            item.getTechnologyName()
                                    );

                                    technology.setTechnologyType(
                                            item.getTechnologyType()
                                    );

                                    return technology;
                                })
                                .toList()
        );

        return response;
    }

    private PublicProjectResponse.PublicProjectMetric toPublicMetric(
            ProjectImpactMetricResponse source
    ) {

        PublicProjectResponse.PublicProjectMetric response =
                new PublicProjectResponse.PublicProjectMetric();

        response.setImpactMetricId(source.getImpactMetricId());
        response.setMetricLabel(source.getMetricLabel());
        response.setMetricValue(source.getMetricValue());
        response.setMetricUnit(source.getMetricUnit());
        response.setDescription(source.getDescription());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }

    private boolean isMediaActive(ProjectMediaResponse media) {

        if (media.getMediaId() == null) {
            return false;
        }

        return mediaRepository.findById(media.getMediaId())
                .map(asset -> Boolean.TRUE.equals(asset.getIsActive()))
                .orElse(false);
    }

    private PublicProjectResponse.PublicProjectMedia toPublicMedia(
            ProjectMediaResponse source
    ) {

        PublicProjectResponse.PublicProjectMedia response =
                new PublicProjectResponse.PublicProjectMedia();

        response.setProjectMediaId(source.getProjectMediaId());
        response.setFileName(source.getFileName());
        response.setFileType(source.getFileType());
        response.setMimeType(source.getMimeType());
        response.setMediaUrl(source.getMediaUrl());
        response.setCaption(source.getCaption());
        response.setDisplayOrder(source.getDisplayOrder());

        return response;
    }
}

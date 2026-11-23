
package com.prem.portfolio.portfolio.service.impl;

import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.repository.MediaRepository;
import com.prem.portfolio.portfolio.dto.request.ProjectMediaRequest;
import com.prem.portfolio.portfolio.dto.response.ProjectMediaResponse;
import com.prem.portfolio.portfolio.entity.Project;
import com.prem.portfolio.portfolio.entity.ProjectMedia;
import com.prem.portfolio.portfolio.mapper.ProjectMediaMapper;
import com.prem.portfolio.portfolio.repository.ProjectMediaRepository;
import com.prem.portfolio.portfolio.repository.ProjectRepository;
import com.prem.portfolio.portfolio.service.ProjectMediaService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectMediaServiceImpl implements ProjectMediaService {

    private final ProjectMediaRepository projectMediaRepository;
    private final ProjectRepository projectRepository;
    private final MediaRepository mediaRepository;
    private final ProjectMediaMapper projectMediaMapper;

    public ProjectMediaServiceImpl(
            ProjectMediaRepository projectMediaRepository,
            ProjectRepository projectRepository,
            MediaRepository mediaRepository,
            ProjectMediaMapper projectMediaMapper
    ) {
        this.projectMediaRepository = projectMediaRepository;
        this.projectRepository = projectRepository;
        this.mediaRepository = mediaRepository;
        this.projectMediaMapper = projectMediaMapper;
    }

    @Override
    public ProjectMediaResponse addMedia(
            Long projectId,
            ProjectMediaRequest request
    ) {

        validateActiveProject(projectId);
        validateActiveMedia(request.getMediaId());
        validateDuplicateMedia(projectId, request.getMediaId(), null);

        ProjectMedia projectMedia =
                projectMediaMapper.toEntity(projectId, request);

        ProjectMedia savedMedia =
                projectMediaRepository.save(projectMedia);

        return buildResponse(savedMedia);
    }

    @Override
    public ProjectMediaResponse updateMedia(
            Long projectId,
            Long projectMediaId,
            ProjectMediaRequest request
    ) {

        validateActiveProject(projectId);

        ProjectMedia projectMedia =
                getOwnedActiveMedia(projectId, projectMediaId);

        validateActiveMedia(request.getMediaId());

        validateDuplicateMedia(
                projectId,
                request.getMediaId(),
                projectMediaId
        );

        projectMediaMapper.updateEntity(projectMedia, request);

        ProjectMedia updatedMedia =
                projectMediaRepository.save(projectMedia);

        return buildResponse(updatedMedia);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectMediaResponse getMediaById(
            Long projectId,
            Long projectMediaId
    ) {

        validateActiveProject(projectId);

        ProjectMedia projectMedia =
                getOwnedActiveMedia(projectId, projectMediaId);

        return buildResponse(projectMedia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectMediaResponse> getProjectMedia(
            Long projectId,
            Boolean active
    ) {

        validateActiveProject(projectId);

        return projectMediaRepository
                .findProjectMedia(projectId, active)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void deleteMedia(
            Long projectId,
            Long projectMediaId
    ) {

        validateActiveProject(projectId);

        ProjectMedia projectMedia =
                getOwnedActiveMedia(projectId, projectMediaId);

        projectMedia.setActive(false);

        projectMediaRepository.save(projectMedia);
    }

    private void validateActiveProject(Long projectId) {

        projectRepository.findByProjectIdAndActiveTrue(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active project not found"
                        )
                );
    }

    private void validateActiveMedia(Long mediaId) {

        MediaAsset media = mediaRepository.findById(mediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Media not found")
                );

        if (!Boolean.TRUE.equals(media.getIsActive())) {
            throw new IllegalArgumentException("Media is inactive");
        }
    }

    private void validateDuplicateMedia(
            Long projectId,
            Long mediaId,
            Long currentProjectMediaId
    ) {

        boolean duplicate = projectMediaRepository
                .findProjectMedia(projectId, true)
                .stream()
                .anyMatch(existing ->
                        existing.getMediaId().equals(mediaId)
                        && !existing.getProjectMediaId()
                                .equals(currentProjectMediaId)
                );

        if (duplicate) {
            throw new IllegalArgumentException(
                    "This media is already attached to the project"
            );
        }
    }

    private ProjectMedia getOwnedActiveMedia(
            Long projectId,
            Long projectMediaId
    ) {

        ProjectMedia projectMedia = projectMediaRepository
                .findByProjectMediaIdAndActiveTrue(projectMediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active project media not found"
                        )
                );

        if (!projectMedia.getProjectId().equals(projectId)) {
            throw new IllegalArgumentException(
                    "Media does not belong to this project"
            );
        }

        return projectMedia;
    }

    private ProjectMediaResponse buildResponse(
            ProjectMedia projectMedia
    ) {

        ProjectMediaResponse response =
                projectMediaMapper.toResponse(projectMedia);

        mediaRepository.findById(projectMedia.getMediaId())
                .ifPresent(media -> {
                    response.setFileName(media.getFileName());
                    response.setFileType(media.getFileType());
                    response.setMimeType(media.getMimeType());
                    response.setMediaUrl(media.getFileUrl());
                });

        return response;
    }
}

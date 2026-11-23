
package com.prem.portfolio.media.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.prem.portfolio.media.dto.response.MediaResponseDTO;
import com.prem.portfolio.media.entity.MediaAsset;
import com.prem.portfolio.media.mapper.MediaMapper;
import com.prem.portfolio.media.repository.MediaRepository;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Value("${app.media.upload-dir:uploads/media}")
    private String uploadDirectory;

    public MediaServiceImpl(
            MediaRepository mediaRepository,
            MediaMapper mediaMapper) {

        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public MediaResponseDTO uploadMedia(
            MultipartFile file,
            String altText) {

        validateFile(file);

        String originalFileName = getSafeFileName(file.getOriginalFilename());
        String extension = getFileExtension(originalFileName);
        String fileType = determineFileType(extension);

        String storedFileName = UUID.randomUUID() + "." + extension;

        Path uploadPath = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        Path targetPath = uploadPath.resolve(storedFileName).normalize();

        // Prevent files from being stored outside the upload directory.
        if (!targetPath.startsWith(uploadPath)) {
            throw new IllegalArgumentException("Invalid file path.");
        }

        try {
            Files.createDirectories(uploadPath);

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to store uploaded file.", e
            );
        }

        MediaAsset mediaAsset = new MediaAsset();

        mediaAsset.setFileName(originalFileName);
        mediaAsset.setFileKey(storedFileName);
        mediaAsset.setFileUrl(
                "/api/v1/media/files/" + storedFileName
        );
        mediaAsset.setFileType(fileType);
        mediaAsset.setMimeType(file.getContentType());
        mediaAsset.setFileSize(file.getSize());
        mediaAsset.setStorageProvider("LOCAL");
        mediaAsset.setAltText(altText);
        mediaAsset.setIsActive(true);

        try {
            MediaAsset savedMedia = mediaRepository.save(mediaAsset);
            return mediaMapper.toResponseDTO(savedMedia);

        } catch (RuntimeException e) {

            try {
                Files.deleteIfExists(targetPath);
            } catch (IOException cleanupException) {
                e.addSuppressed(cleanupException);
            }

            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponseDTO> getAllMedia() {

        return mediaRepository.findByIsActiveTrue()
                .stream()
                .map(mediaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MediaResponseDTO getMediaById(Long mediaId) {

        MediaAsset mediaAsset = mediaRepository.findById(mediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Media not found with ID: " + mediaId
                        )
                );

        if (!Boolean.TRUE.equals(mediaAsset.getIsActive())) {
            throw new IllegalArgumentException(
                    "Media is inactive."
            );
        }

        return mediaMapper.toResponseDTO(mediaAsset);
    }

    @Override
    public void deactivateMedia(Long mediaId) {

        MediaAsset mediaAsset = mediaRepository.findById(mediaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Media not found with ID: " + mediaId
                        )
                );

        mediaAsset.setIsActive(false);

        mediaRepository.save(mediaAsset);
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please select a file to upload."
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "File size cannot exceed 10 MB."
            );
        }

        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException(
                    "Invalid file name."
            );
        }
    }

    private String getSafeFileName(String originalFileName) {

        String normalizedName = originalFileName.replace("\\", "/");

        return normalizedName.substring(
                normalizedName.lastIndexOf("/") + 1
        );
    }

    private String getFileExtension(String fileName) {

        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex <= 0 || dotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException(
                    "File must have a valid extension."
            );
        }

        return fileName.substring(dotIndex + 1)
                .toLowerCase(Locale.ROOT);
    }

    private String determineFileType(String extension) {

        return switch (extension) {

            case "jpg", "jpeg", "png", "gif", "webp" ->
                    "IMAGE";

            case "pdf" ->
                    "PDF";

            case "doc", "docx", "xls", "xlsx",
                 "ppt", "pptx", "txt", "csv" ->
                    "DOCUMENT";

            case "mp4", "webm", "mov" ->
                    "VIDEO";

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported file format: " + extension
                    );
        };
    }
}

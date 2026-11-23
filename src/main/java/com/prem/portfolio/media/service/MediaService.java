
package com.prem.portfolio.media.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.prem.portfolio.media.dto.response.MediaResponseDTO;

public interface MediaService {

    // Upload a new media file
    MediaResponseDTO uploadMedia(
            MultipartFile file,
            String altText
    );

    // Get all active media files
    List<MediaResponseDTO> getAllMedia();

    // Get media by ID
    MediaResponseDTO getMediaById(Long mediaId);

    // Deactivate media (soft delete)
    void deactivateMedia(Long mediaId);
}

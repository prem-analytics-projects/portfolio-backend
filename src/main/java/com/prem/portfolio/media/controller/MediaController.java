
package com.prem.portfolio.media.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prem.portfolio.media.dto.response.MediaResponseDTO;
import com.prem.portfolio.media.service.MediaService;

@RestController
@RequestMapping("/api/v1/admin/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    // Upload media file
    @PostMapping("/upload")
    public ResponseEntity<MediaResponseDTO> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "altText", required = false)
            String altText) {

        MediaResponseDTO response =
                mediaService.uploadMedia(file, altText);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get all active media
    @GetMapping
    public ResponseEntity<List<MediaResponseDTO>> getAllMedia() {

        return ResponseEntity.ok(
                mediaService.getAllMedia()
        );
    }

    // Get media by ID
    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaResponseDTO> getMediaById(
            @PathVariable Long mediaId) {

        return ResponseEntity.ok(
                mediaService.getMediaById(mediaId)
        );
    }

    // Deactivate media
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deactivateMedia(
            @PathVariable Long mediaId) {

        mediaService.deactivateMedia(mediaId);

        return ResponseEntity.noContent().build();
    }
}

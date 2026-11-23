
package com.prem.portfolio.media.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media/files")
public class MediaFileController {

    @Value("${app.media.upload-dir:uploads/media}")
    private String uploadDirectory;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getMediaFile(
            @PathVariable String fileName) throws IOException {

        // Reject path traversal and directory separators.
        if (fileName == null ||
                fileName.isBlank() ||
                fileName.contains("/") ||
                fileName.contains("\\") ||
                fileName.contains("..")) {

            return ResponseEntity.badRequest().build();
        }

        Path uploadPath = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        Path filePath = uploadPath.resolve(fileName)
                .normalize();

        if (!filePath.startsWith(uploadPath) ||
                !Files.exists(filePath) ||
                !Files.isRegularFile(filePath)) {

            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(filePath);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (contentType != null) {
            try {
                mediaType = MediaType.parseMediaType(contentType);
            } catch (Exception ignored) {
                // Retain the default content type.
            }
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName + "\""
                )
                .body(resource);
    }
}


package com.prem.portfolio.media.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prem.portfolio.media.entity.MediaAsset;

public interface MediaRepository extends JpaRepository<MediaAsset, Long> {

    // Find all active media files
    List<MediaAsset> findByIsActiveTrue();

    // Find media by file type
    List<MediaAsset> findByFileTypeAndIsActiveTrue(String fileType);

    // Find media by storage provider
    List<MediaAsset> findByStorageProviderAndIsActiveTrue(String storageProvider);

}


package com.prem.portfolio.media.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.media.dto.response.MediaResponseDTO;
import com.prem.portfolio.media.entity.MediaAsset;

@Component
public class MediaMapper {

    public MediaResponseDTO toResponseDTO(MediaAsset mediaAsset) {

        if (mediaAsset == null) {
            return null;
        }

        return new MediaResponseDTO(
                mediaAsset.getMediaId(),
                mediaAsset.getFileName(),
                mediaAsset.getFileUrl(),
                mediaAsset.getFileType(),
                mediaAsset.getMimeType(),
                mediaAsset.getFileSize(),
                mediaAsset.getStorageProvider(),
                mediaAsset.getAltText(),
                mediaAsset.getIsActive(),
                mediaAsset.getCreatedAt()
        );
    }
}

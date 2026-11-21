package com.prem.portfolio.common.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public record ErrorResponse(
        boolean success,
        String message,
        Map<String, String> errors,
        String path,
        OffsetDateTime timestamp
) {
}
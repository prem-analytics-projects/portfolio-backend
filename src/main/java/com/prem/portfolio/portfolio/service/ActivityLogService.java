
package com.prem.portfolio.portfolio.service;

import org.springframework.data.domain.Page;

import com.prem.portfolio.portfolio.dto.response.ActivityLogResponse;

public interface ActivityLogService {

    Page<ActivityLogResponse> getActivityLogs(
            Long userId,
            String action,
            String schemaName,
            String tableName,
            Long recordId,
            int page,
            int size
    );

    ActivityLogResponse getActivityLogById(Long logId);
}

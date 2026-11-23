
package com.prem.portfolio.portfolio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prem.portfolio.portfolio.dto.response.ActivityLogResponse;
import com.prem.portfolio.portfolio.entity.ActivityLog;
import com.prem.portfolio.portfolio.mapper.ActivityLogMapper;
import com.prem.portfolio.portfolio.repository.ActivityLogRepository;
import com.prem.portfolio.portfolio.service.ActivityLogService;

@Service
@Transactional(readOnly = true)
public class ActivityLogServiceImpl implements ActivityLogService {

    private static final int MAX_PAGE_SIZE = 100;

    private final ActivityLogRepository activityLogRepository;
    private final ActivityLogMapper activityLogMapper;

    public ActivityLogServiceImpl(
            ActivityLogRepository activityLogRepository,
            ActivityLogMapper activityLogMapper) {

        this.activityLogRepository = activityLogRepository;
        this.activityLogMapper = activityLogMapper;
    }

    @Override
    public Page<ActivityLogResponse> getActivityLogs(
            Long userId,
            String action,
            String schemaName,
            String tableName,
            Long recordId,
            int page,
            int size) {

        validatePagination(page, size);

        return activityLogRepository.searchActivityLogs(
                        userId,
                        normalize(action),
                        normalize(schemaName),
                        normalize(tableName),
                        recordId,
                        PageRequest.of(page, size)
                )
                .map(activityLogMapper::toResponse);
    }

    @Override
    public ActivityLogResponse getActivityLogById(Long logId) {

        ActivityLog activityLog = activityLogRepository.findById(logId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Activity log not found with ID: " + logId
                        ));

        return activityLogMapper.toResponse(activityLog);
    }

    private void validatePagination(int page, int size) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative."
            );
        }

        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and "
                            + MAX_PAGE_SIZE + "."
            );
        }
    }

    private String normalize(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}

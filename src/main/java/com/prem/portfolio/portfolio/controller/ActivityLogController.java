
package com.prem.portfolio.portfolio.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prem.portfolio.portfolio.dto.response.ActivityLogResponse;
import com.prem.portfolio.portfolio.service.ActivityLogService;

@RestController
@RequestMapping("/api/v1/admin/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ResponseEntity<Page<ActivityLogResponse>> getActivityLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String schemaName,
            @RequestParam(required = false) String tableName,
            @RequestParam(required = false) Long recordId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ActivityLogResponse> logs =
                activityLogService.getActivityLogs(
                        userId,
                        action,
                        schemaName,
                        tableName,
                        recordId,
                        page,
                        size
                );

        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<ActivityLogResponse> getActivityLogById(
            @PathVariable Long logId) {

        return ResponseEntity.ok(
                activityLogService.getActivityLogById(logId)
        );
    }
}


package com.prem.portfolio.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.prem.portfolio.portfolio.dto.response.ActivityLogResponse;
import com.prem.portfolio.portfolio.entity.ActivityLog;

@Component
public class ActivityLogMapper {

    public ActivityLogResponse toResponse(ActivityLog entity) {

        if (entity == null) {
            return null;
        }

        ActivityLogResponse response = new ActivityLogResponse();

        response.setLogId(entity.getLogId());
        response.setUserId(entity.getUserId());
        response.setAction(entity.getAction());
        response.setSchemaName(entity.getSchemaName());
        response.setTableName(entity.getTableName());
        response.setRecordId(entity.getRecordId());
        response.setOldValues(entity.getOldValues());
        response.setNewValues(entity.getNewValues());
        response.setIpAddress(entity.getIpAddress());
        response.setUserAgent(entity.getUserAgent());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }
}

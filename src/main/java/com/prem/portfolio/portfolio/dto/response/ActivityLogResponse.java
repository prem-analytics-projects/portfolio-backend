
package com.prem.portfolio.portfolio.dto.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.JsonNode;

public class ActivityLogResponse {

    private Long logId;
    private Long userId;
    private String action;
    private String schemaName;
    private String tableName;
    private Long recordId;
    private JsonNode oldValues;
    private JsonNode newValues;
    private String ipAddress;
    private String userAgent;
    private OffsetDateTime createdAt;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public JsonNode getOldValues() {
        return oldValues;
    }

    public void setOldValues(JsonNode oldValues) {
        this.oldValues = oldValues;
    }

    public JsonNode getNewValues() {
        return newValues;
    }

    public void setNewValues(JsonNode newValues) {
        this.newValues = newValues;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


package com.prem.portfolio.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prem.portfolio.portfolio.entity.ActivityLog;

public interface ActivityLogRepository
        extends JpaRepository<ActivityLog, Long> {

    @Query("""
        SELECT a FROM ActivityLog a
        WHERE (:userId IS NULL OR a.userId = :userId)
          AND (:action IS NULL OR LOWER(a.action) = LOWER(:action))
          AND (:schemaName IS NULL OR LOWER(a.schemaName) = LOWER(:schemaName))
          AND (:tableName IS NULL OR LOWER(a.tableName) = LOWER(:tableName))
          AND (:recordId IS NULL OR a.recordId = :recordId)
        ORDER BY a.createdAt DESC, a.logId DESC
        """)
    Page<ActivityLog> searchActivityLogs(
            @Param("userId") Long userId,
            @Param("action") String action,
            @Param("schemaName") String schemaName,
            @Param("tableName") String tableName,
            @Param("recordId") Long recordId,
            Pageable pageable
    );
}

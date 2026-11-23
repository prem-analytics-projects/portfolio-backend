
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.ContactMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ContactMessageRepository
        extends JpaRepository<ContactMessage, Long> {

    @Query("""
        SELECT m FROM ContactMessage m
        WHERE (:messageStatus IS NULL
               OR m.messageStatus = :messageStatus)
        ORDER BY m.receivedAt DESC, m.messageId DESC
        """)
    List<ContactMessage> findMessages(
            @Param("messageStatus") String messageStatus
    );

    Optional<ContactMessage> findByMessageId(Long messageId);
}

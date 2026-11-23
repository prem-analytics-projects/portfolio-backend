
package com.prem.portfolio.portfolio.repository;

import com.prem.portfolio.portfolio.entity.ContactDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactDetailRepository
        extends JpaRepository<ContactDetail, Long> {

    @Query("""
        SELECT c FROM ContactDetail c
        WHERE c.profileId = :profileId
        AND (:active IS NULL OR c.active = :active)
        ORDER BY c.displayOrder ASC, c.contactId ASC
        """)
    List<ContactDetail> findContactDetails(
            @Param("profileId") Long profileId,
            @Param("active") Boolean active
    );

    Optional<ContactDetail> findByContactIdAndActiveTrue(Long contactId);
}

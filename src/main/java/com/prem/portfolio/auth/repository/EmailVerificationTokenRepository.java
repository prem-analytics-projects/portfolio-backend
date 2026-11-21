
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailVerificationTokenRepository
        extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByTokenHash(
            String tokenHash
    );

    List<EmailVerificationToken> findByUser_UserId(
            Long userId
    );

    List<EmailVerificationToken> findByUser_UserIdAndUsedAtIsNull(
            Long userId
    );

    void deleteByUser_UserId(Long userId);

    void deleteByExpiresAtBefore(OffsetDateTime dateTime);
}

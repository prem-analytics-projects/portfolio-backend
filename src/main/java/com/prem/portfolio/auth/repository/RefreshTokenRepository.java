
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    boolean existsByTokenHash(String tokenHash);

    List<RefreshToken> findByUser_UserId(Long userId);

    List<RefreshToken> findByUser_UserIdAndRevokedAtIsNull(
            Long userId
    );

    void deleteByUser_UserId(Long userId);

    void deleteByExpiresAtBefore(OffsetDateTime dateTime);
}

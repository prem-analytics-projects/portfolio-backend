
package com.prem.portfolio.auth.service;

import com.prem.portfolio.auth.entity.RefreshToken;
import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.repository.RefreshTokenRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final long expiryDays;

    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${app.refresh-token.expiry-days:7}")
            long expiryDays
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.expiryDays = expiryDays;
    }

    /**
     * Generates a refresh token and stores only its hash.
     *
     * Returns the original token to the caller.
     */
    @Transactional
    public String createRefreshToken(User user) {

        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException(
                    "A saved user is required"
            );
        }

        String rawToken = generateSecureToken();

        String tokenHash = hashToken(rawToken);

        OffsetDateTime expiresAt =
                OffsetDateTime.now(ZoneOffset.UTC)
                        .plusDays(expiryDays);

        RefreshToken refreshToken =
                new RefreshToken(
                        user,
                        tokenHash,
                        expiresAt
                );

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    /**
     * Validates a refresh token.
     *
     * Returns the database entity only if the token
     * exists, has not expired and has not been revoked.
     */
    @Transactional(readOnly = true)
    public RefreshToken validateRefreshToken(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            throw new IllegalArgumentException(
                    "Refresh token is required"
            );
        }

        String tokenHash = hashToken(rawToken);

        RefreshToken refreshToken =
                refreshTokenRepository.findByTokenHash(tokenHash)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid refresh token"
                                )
                        );

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        if (refreshToken.getRevokedAt() != null) {
            throw new IllegalStateException(
                    "Refresh token has been revoked"
            );
        }

        if (!refreshToken.getExpiresAt().isAfter(now)) {
            throw new IllegalStateException(
                    "Refresh token has expired"
            );
        }

        return refreshToken;
    }

    /**
     * Revokes the old refresh token and issues a new one.
     *
     * The operation runs within one database transaction.
     */
    @Transactional
    public String rotateRefreshToken(String oldRawToken) {

        RefreshToken oldToken =
                validateRefreshToken(oldRawToken);

        User user = oldToken.getUser();

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        // Revoke the old token.
        oldToken.setRevokedAt(now);

        refreshTokenRepository.save(oldToken);

        // Generate and store a new refresh token.
        return createRefreshToken(user);
    }

    /**
     * Revokes a single refresh token.
     *
     * Repeated logout requests are harmless.
     */
    @Transactional
    public void revokeRefreshToken(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            return;
        }

        String tokenHash = hashToken(rawToken);

        refreshTokenRepository.findByTokenHash(tokenHash)
                .ifPresent(refreshToken -> {

                    if (refreshToken.getRevokedAt() == null) {

                        refreshToken.setRevokedAt(
                                OffsetDateTime.now(ZoneOffset.UTC)
                        );

                        refreshTokenRepository.save(refreshToken);
                    }
                });
    }

    /**
     * Revokes all active refresh tokens belonging to a user.
     */
    @Transactional
    public void revokeAllUserTokens(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "User ID is required"
            );
        }

        List<RefreshToken> activeTokens =
                refreshTokenRepository
                        .findByUser_UserIdAndRevokedAtIsNull(userId);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        for (RefreshToken token : activeTokens) {

            token.setRevokedAt(now);
        }

        refreshTokenRepository.saveAll(activeTokens);
    }

    /**
     * Generates a cryptographically secure random token.
     */
    private String generateSecureToken() {

        byte[] randomBytes = new byte[64];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    /**
     * Hashes the raw token using SHA-256.
     */
    private String hashToken(String rawToken) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8)
            );

            return Base64.getEncoder()
                    .encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException exception) {

            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable",
                    exception
            );
        }
    }
}

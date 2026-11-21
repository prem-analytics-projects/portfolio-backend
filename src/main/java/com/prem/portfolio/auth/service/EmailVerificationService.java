
package com.prem.portfolio.auth.service;

import com.prem.portfolio.auth.entity.EmailVerificationToken;
import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.repository.EmailVerificationTokenRepository;
import com.prem.portfolio.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    private final String verificationUrl;
    private final String senderEmail;
    private final long expiryMinutes;

    private final SecureRandom secureRandom = new SecureRandom();

    public EmailVerificationService(
            EmailVerificationTokenRepository tokenRepository,
            UserRepository userRepository,
            JavaMailSender mailSender,
            @Value("${app.email-verification.url}") String verificationUrl,
            @Value("${app.mail.from}") String senderEmail,
            @Value("${app.email-verification.expiry-minutes:30}") long expiryMinutes
    ) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.verificationUrl = verificationUrl;
        this.senderEmail = senderEmail;
        this.expiryMinutes = expiryMinutes;
    }

    /**
     * Generates and sends a new email verification token.
     * Only the hashed token is stored in the database.
     */
    @Transactional
    public void createAndSendVerificationToken(User user) {

        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException(
                    "A saved user is required"
            );
        }

        if (user.getEmailVerifiedAt() != null) {
            throw new IllegalStateException(
                    "Email is already verified"
            );
        }

        String rawToken = generateSecureToken();
        String tokenHash = hashToken(rawToken);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime expiry = now.plusMinutes(expiryMinutes);

        // Invalidate previously generated verification tokens.
        tokenRepository.deleteByUser_UserId(user.getUserId());

        EmailVerificationToken verificationToken =
                new EmailVerificationToken(
                        user,
                        tokenHash,
                        expiry
                );

        tokenRepository.save(verificationToken);

        // Send original token only through email.
        sendVerificationEmail(user, rawToken);
    }

    /**
     * Verifies the supplied token and marks the email as verified.
     */
    @Transactional
    public void verifyEmail(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            throw new IllegalArgumentException(
                    "Verification token is required"
            );
        }

        String tokenHash = hashToken(rawToken);

        EmailVerificationToken verificationToken =
                tokenRepository.findByTokenHash(tokenHash)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid verification token"
                                )
                        );

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        if (verificationToken.getUsedAt() != null) {
            throw new IllegalStateException(
                    "Verification token has already been used"
            );
        }

        if (!verificationToken.getExpiresAt().isAfter(now)) {
            throw new IllegalStateException(
                    "Verification token has expired"
            );
        }

        User user = verificationToken.getUser();

        if (user.getEmailVerifiedAt() != null) {
            throw new IllegalStateException(
                    "Email is already verified"
            );
        }

        // Mark the token as used.
        verificationToken.setUsedAt(now);
        tokenRepository.save(verificationToken);

        // Mark the user's email as verified.
        user.setEmailVerifiedAt(now);
        userRepository.save(user);
    }

    /**
     * Generates a cryptographically secure random token.
     */
    private String generateSecureToken() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    /**
     * Creates a SHA-256 hash of the supplied token.
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

    /**
     * Sends verification email through configured SMTP.
     */
    private void sendVerificationEmail(
            User user,
            String rawToken
    ) {

        String verificationLink =
                verificationUrl + "?token=" + rawToken;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(user.getEmail());
        message.setSubject("Verify your Portfolio account");

        message.setText(
                "Hello " + user.getFirstName() + ",\n\n"
                + "Thank you for registering.\n\n"
                + "Please verify your email address by clicking "
                + "the link below:\n\n"
                + verificationLink + "\n\n"
                + "This link expires in " + expiryMinutes
                + " minutes.\n\n"
                + "If you did not create this account, "
                + "please ignore this email.\n\n"
                + "Regards,\n"
                + "Portfolio Team"
        );

        mailSender.send(message);
    }
}

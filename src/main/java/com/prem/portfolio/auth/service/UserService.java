
package com.prem.portfolio.auth.service;

import com.prem.portfolio.auth.entity.User;
import com.prem.portfolio.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {

        String normalizedEmail = normalizeEmail(email);

        return userRepository
                .findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    public boolean emailExists(String email) {

        return userRepository.existsByEmailIgnoreCase(
                normalizeEmail(email)
        );
    }

    @Transactional
    public User saveUser(User user) {

        return userRepository.save(user);
    }

    private String normalizeEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        return email.trim().toLowerCase(Locale.ROOT);
    }
}

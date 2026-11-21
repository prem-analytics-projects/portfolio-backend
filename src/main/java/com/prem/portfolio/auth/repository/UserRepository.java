
package com.prem.portfolio.auth.repository;

import com.prem.portfolio.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByUserIdAndEmailVerifiedAtIsNotNull(Long userId);
}

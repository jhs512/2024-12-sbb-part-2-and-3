package com.mysite.sbb.domain.user.password;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, String> {
    Optional<PasswordToken> findByToken(String token);
}

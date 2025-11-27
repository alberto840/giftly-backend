package com.diplomado.diplomado.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity, Integer> {
    Optional<AuthEntity> findByEmail(String email);
}

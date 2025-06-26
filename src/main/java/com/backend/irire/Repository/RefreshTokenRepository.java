package com.backend.irire.Repository;

import com.backend.irire.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String refreshToken);
    void deleteByUserId(Long id);
}

package com.backend.irire.Service;

import com.backend.irire.Model.RefreshToken;
import com.backend.irire.Model.User;
import com.backend.irire.Repository.RefreshTokenRepository;
import com.backend.irire.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }


        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString()); // Use random UUID for refresh token
        refreshToken.setExpires(Instant.now().plusMillis(604_800_000)); // 7 days
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isRevoked() || token.getExpires().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token is expired or revoked");
        }
        return token;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
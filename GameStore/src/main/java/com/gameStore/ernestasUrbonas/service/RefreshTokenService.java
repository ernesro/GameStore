package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.exception.ForbiddenException;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.model.RefreshToken;
import com.gameStore.ernestasUrbonas.model.Role;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import com.gameStore.ernestasUrbonas.repository.RefreshTokenRepository;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import com.gameStore.ernestasUrbonas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /**
     * Create a new RefreshToken.
     *
     * @param username .
     * @return The created token.
     */
    public String createRefreshToken(String username) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token not found "));

        if (refreshToken.isRevoked()) {
            throw new ForbiddenException("Forbidden, revoked token ");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ForbiddenException("Forbidden, expired token ");
        }

        return refreshToken;
    }

    public void revokeRefreshToken(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }

    public String refreshAccessToken(String token) {
        RefreshToken refreshToken = validateRefreshToken(token);

        UserEntity user = userRepository.findByUsername(refreshToken.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<String> roles = user.getRoleEntities().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return jwtUtil.generateToken(refreshToken.getUsername(), roles);
    }
}

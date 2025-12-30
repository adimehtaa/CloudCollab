package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.model.RefreshToken;
import cloud.devyard.cloudcollab.repository.RefreshTokenRepository;
import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${app.jwt.refresh-expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findById(userId).orElseThrow(()-> new BadRequestException("User not found")))
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .token(UUID.randomUUID().toString())
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new BadRequestException("Refresh token has expired. Please login again.");
        }
        return token;
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        userRepository.findById(userId).ifPresent(user ->
                refreshTokenRepository.deleteByUser(user)
        );
    }
}

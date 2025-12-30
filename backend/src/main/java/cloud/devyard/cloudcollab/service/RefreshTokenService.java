package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken createRefreshToken(Long userId);
    public RefreshToken verifyExpiration(RefreshToken token);
    public void deleteByUserId(Long userId);
}

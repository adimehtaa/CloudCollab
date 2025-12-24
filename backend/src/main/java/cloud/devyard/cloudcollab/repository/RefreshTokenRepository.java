package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.RefreshToken;
import cloud.devyard.cloudcollab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}

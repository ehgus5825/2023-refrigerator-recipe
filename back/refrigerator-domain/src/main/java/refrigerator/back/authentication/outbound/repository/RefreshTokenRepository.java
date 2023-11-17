package refrigerator.back.authentication.outbound.repository;

import org.springframework.data.repository.CrudRepository;
import refrigerator.back.authentication.application.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}

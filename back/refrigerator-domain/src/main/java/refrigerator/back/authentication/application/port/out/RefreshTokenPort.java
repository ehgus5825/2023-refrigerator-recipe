package refrigerator.back.authentication.application.port.out;

import refrigerator.back.authentication.application.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenPort {
    void save(RefreshToken refreshToken);
    Optional<RefreshToken> getToken(String token);
}

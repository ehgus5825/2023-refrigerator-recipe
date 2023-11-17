package refrigerator.back.authentication.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.outbound.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.*;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenPort{

    private final RefreshTokenRepository repository;

    @Override
    public void save(RefreshToken refreshToken) {
        repository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> getToken(String token) {
        return repository.findRefreshTokenByToken(token);
    }
}

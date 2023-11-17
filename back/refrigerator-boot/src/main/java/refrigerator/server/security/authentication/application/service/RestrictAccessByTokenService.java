package refrigerator.server.security.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.security.authentication.application.usecase.RestrictAccessUseCase;
import refrigerator.server.security.exception.JsonWebTokenException;

@Service
@RequiredArgsConstructor
public class RestrictAccessByTokenService implements RestrictAccessUseCase {

    private final RefreshTokenPort refreshTokenPort;

    @Override
    public Boolean restrictAccessToTokens(String refreshToken) {
        RefreshToken token = refreshTokenPort.getToken(refreshToken)
                .orElseThrow(() -> new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_TOKEN));
        token.toInvalidate();
        refreshTokenPort.save(token);
        return true;
    }

}

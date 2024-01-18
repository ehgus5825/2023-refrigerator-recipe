package refrigerator.server.api.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.api.authentication.application.usecase.RestrictAccessUseCase;
import refrigerator.server.api.authentication.inbound.cookie.RefreshTokenCookie;
import refrigerator.server.security.common.exception.JsonWebTokenException;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class RestrictAccessByTokenService implements RestrictAccessUseCase {

    private final RefreshTokenPort refreshTokenPort;

    @Override
    public Cookie restrictAccessToTokens(Cookie[] cookies) {

        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String refreshToken = refreshTokenCookie.get(cookies).getValue();

        RefreshToken token = refreshTokenPort.getToken(refreshToken)
                .orElseThrow(() -> new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_TOKEN));

        token.toInvalidate();

        refreshTokenPort.save(token);

        return refreshTokenCookie.delete();
    }

}

package refrigerator.server.security.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;
import refrigerator.server.security.authentication.application.usecase.LoginUseCase;
import refrigerator.server.security.authentication.application.TokenDto;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.security.token.EmailAuthenticationToken;

import java.util.Collection;
import java.util.Objects;

import static refrigerator.server.security.config.JsonWebTokenKey.*;
import static refrigerator.server.security.config.JsonWebTokenKey.REFRESH_TOKEN_EXPIRE_TIME;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final AuthenticationProvider authenticationProvider;
    private final JsonWebTokenUseCase jwtService;
    private final RefreshTokenPort saveRefreshTokenPort;

    /* 인증 객체 가져오기 -> 토큰 생성 -> refreshToken 저장 */
    @Override
    public TokenDto login(String email, String password) {
        Authentication authentication = authenticationProvider.authenticate(
                new EmailAuthenticationToken(email, password));
        String authority = getAuthority(authentication.getAuthorities());
        String accessToken = createToken(email, authority, ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = createToken(email, authority, REFRESH_TOKEN_EXPIRE_TIME);
        saveRefreshTokenPort.save(new RefreshToken(email, refreshToken, true));
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String createToken(String username, String authority, long time){
        return jwtService.createToken(username, authority, time);
    }

    private String getAuthority(Collection<? extends GrantedAuthority> authorities){
        return authorities
                .stream().map(Objects::toString)
                .findFirst()
                .orElseThrow(() -> new BusinessException(AuthenticationExceptionType.NOT_FOUND_AUTHORITY));
    }
}

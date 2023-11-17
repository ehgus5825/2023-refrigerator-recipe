package refrigerator.server.security.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.server.security.authentication.application.TokenDto;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;
import refrigerator.server.security.authentication.application.usecase.ReissueUseCase;

import static refrigerator.server.security.config.JsonWebTokenKey.ACCESS_TOKEN_EXPIRE_TIME;
import static refrigerator.server.security.config.JsonWebTokenKey.BEARER_TYPE;

@Service
@RequiredArgsConstructor
public class ReissueService implements ReissueUseCase {

    private final JwtValidationService jwtValidationService;
    private final JsonWebTokenUseCase jwtService;

    @Override
    public TokenDto reissue(String refreshToken) {
        ClaimsDto claims = jwtValidationService.checkValidationByRefreshToken(refreshToken);
        String accessToken = jwtService.createToken(
                claims.getEmail(),
                claims.getRole(),
                ACCESS_TOKEN_EXPIRE_TIME);
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .build();
    }

}

package refrigerator.server.api.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.server.api.authentication.application.usecase.ReissueUseCase;
import refrigerator.server.api.authentication.application.dto.TokenDto;
import refrigerator.server.security.common.dto.ClaimsDto;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;
import refrigerator.server.security.common.exception.JsonWebTokenException;

import static io.jsonwebtoken.lang.Strings.hasText;
import static refrigerator.server.security.common.jwt.JsonWebTokenKey.ACCESS_TOKEN_EXPIRE_TIME;
import static refrigerator.server.security.common.jwt.JsonWebTokenKey.BEARER_TYPE;

@Service
@RequiredArgsConstructor
public class ReissueService implements ReissueUseCase {

    private final JsonWebTokenUseCase jwtService;
    private final RefreshTokenPort refreshTokenPort;

    @Override
    public TokenDto reissue(String refreshToken) {
        // RefreshToken의 유효성을 검사하고 ClaimsDto를 반환
        ClaimsDto claims = checkValidationByRefreshToken(refreshToken);
        // RefreshToken에서 추출한 페이로드 정보(ClaimsDto)로 AccessToken를 다시 만듬  
        String accessToken = jwtService.createToken(
                claims.getEmail(),
                claims.getRole(),
                ACCESS_TOKEN_EXPIRE_TIME);
        // 그리고 반환
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .build();
    }

    public ClaimsDto checkValidationByRefreshToken(String refreshToken){
        // RefreshToken이 조회되고 내부의 accessFlag가 false가 아니라면
        // RefreshToken은 유효함
        RefreshToken token = refreshTokenPort.getToken(refreshToken).orElseThrow(
                () -> new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_TOKEN));
        if (!token.isValidated()){
            throw new JsonWebTokenException(AuthenticationExceptionType.FAIL_ACCESS_BY_TOKEN);
        }
        // 토큰의 payload에서 ClaimsDto의 내용을 뽑음. (email, role, expiration)
        return checkTokenAuthority(refreshToken);
    }

    private ClaimsDto checkTokenAuthority(String token) {
        // 페이로드 분석
        ClaimsDto claims = jwtService.parseClaims(token);
        String role = claims.getRole();
        if (!hasText(role) || !MemberStatus.isValidByCode(role)) {
            throw new JsonWebTokenException(JwtExceptionType.NOT_FOUND_AUTHORITY);
        }
        return claims;
    }
}

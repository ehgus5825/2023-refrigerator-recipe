package refrigerator.server.security.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.security.authentication.application.TokenStatus;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.server.security.exception.JsonWebTokenException;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class JwtValidationService {

    private final JsonWebTokenUseCase jwtService;
    private final RefreshTokenPort refreshTokenPort;

    public ClaimsDto checkValidation(String token) {
        checkTokenStatus(token);
        return checkTokenAuthority(token);
    }

    public ClaimsDto checkValidationByRefreshToken(String refreshToken){
        RefreshToken token = refreshTokenPort.getToken(refreshToken).orElseThrow(
                () -> new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_TOKEN));
        if (!token.isValidated()){
            throw new JsonWebTokenException(AuthenticationExceptionType.FAIL_ACCESS_BY_TOKEN);
        }
        return checkTokenAuthority(refreshToken);
    }

    private ClaimsDto checkTokenAuthority(String token){
        ClaimsDto claims = jwtService.parseClaims(token);
        String role = claims.getRole();
        if (!hasText(role) || !MemberStatus.isValidByCode(role)){
            throw new JsonWebTokenException(JwtExceptionType.NOT_FOUND_AUTHORITY);
        }
        return claims;
    }

    private void checkTokenStatus(String token){
        TokenStatus tokenStatus = jwtService.getTokenStatus(token);
        if (tokenStatus == TokenStatus.EXPIRED){
            throw new JsonWebTokenException(JwtExceptionType.ACCESS_TOKEN_EXPIRED);
        }
        if (tokenStatus == TokenStatus.WRONG){
            throw new JsonWebTokenException(JwtExceptionType.INVALID_ACCESS_TOKEN);
        }
    }
}

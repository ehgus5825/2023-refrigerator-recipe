package refrigerator.server.security.authorization.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.server.security.common.dto.ClaimsDto;
import refrigerator.server.security.common.jwt.TokenStatus;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.server.security.common.exception.JsonWebTokenException;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class JwtValidationService {

    private final JsonWebTokenUseCase jwtService;

    public ClaimsDto checkValidation(String token) {
        // 토큰 상태 확인 (아 밑에서 만료된 토큰도 catch로 받아온다 해도 여기서 걸리구나..)
        checkTokenStatus(token);
        // 토큰의 payload에서 ClaimsDto의 내용을 뽑음. (email, role, expiration)
        return checkTokenAuthority(token);
    }

    private ClaimsDto checkTokenAuthority(String token){
        // 페이로드 분석
        ClaimsDto claims = jwtService.parseClaims(token);
        String role = claims.getRole();
        if (!hasText(role) || !MemberStatus.isValidByCode(role)){
            throw new JsonWebTokenException(JwtExceptionType.NOT_FOUND_AUTHORITY);
        }
        return claims;
    }

    private void checkTokenStatus(String token){
        // getTokenStatus를 통해서 토큰을 보내서 키와 토큰을 이용해 토큰의 상태가 어떤지 받아옴. PASS면 사용가능
        // 아니면 모두 불가능 그래서 아래와 같이 예외 발생
        TokenStatus tokenStatus = jwtService.getTokenStatus(token);
        if (tokenStatus == TokenStatus.EXPIRED){
            throw new JsonWebTokenException(JwtExceptionType.ACCESS_TOKEN_EXPIRED);
        }
        if (tokenStatus == TokenStatus.WRONG){
            throw new JsonWebTokenException(JwtExceptionType.INVALID_ACCESS_TOKEN);
        }
    }
}

package refrigerator.server.security.authorization.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import refrigerator.server.security.common.dto.ClaimsDto;

import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtValidationService jwtValidationService;


    // 근데 여기서 authentication.getName()을 쓰면 token을 받을 수 있음 이유는
    // JwtAuthenticationToken에서 getName()을 오버라이딩 했는데 isAuthenticated가 false라면 token을 반환하기 때문
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        if (token != null){     // token이 null이라는 것은 처음에 토큰 자르기할때 어떤 문제가 있었다는 것
            // 토큰이 유효한지 확인하고 ClaimsDto를 받아옴
            ClaimsDto claims = jwtValidationService.checkValidation(token);
            return new JwtAuthenticationToken(
                    claims.getEmail(),  // ClaimsDto에서 email을 반환하고
                    Set.of(new SimpleGrantedAuthority(claims.getRole()))); // ClaimsDto에서 회원의 상태를 반환함.
        }
        return null; // null이 반환되면 filter에 갔을때 Authentication이 null임
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}

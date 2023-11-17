package refrigerator.server.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.server.security.token.JwtAuthenticationToken;
import refrigerator.server.security.authentication.application.service.JwtValidationService;

import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtValidationService jwtValidationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        if (token != null){
            ClaimsDto claims = jwtValidationService.checkValidation(token);
            return new JwtAuthenticationToken(
                    claims.getEmail(),
                    Set.of(new SimpleGrantedAuthority(claims.getRole())));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}

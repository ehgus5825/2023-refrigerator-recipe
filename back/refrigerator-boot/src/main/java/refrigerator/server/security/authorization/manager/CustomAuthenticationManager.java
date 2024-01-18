package refrigerator.server.security.authorization.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class CustomAuthenticationManager implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;
    // JwtAuthenticationProvider
    // EmailAuthenticationProvider

    public CustomAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Override
    // 아마 필터에서는 Authentication이 jwt관련으로 들어왔을 것임
    // 그래서 provider에서 JwtAuthenticationProvider이 선택됨
    // 그래서 JwtAuthenticationProvider의 authenticate가 실행됨
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : providers) {
            if (provider.supports(authentication.getClass())){
                return provider.authenticate(authentication);
            }
        }
        return null;
    }
}

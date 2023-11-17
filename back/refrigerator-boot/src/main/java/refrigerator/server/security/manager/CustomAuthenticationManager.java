package refrigerator.server.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class CustomAuthenticationManager implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;

    public CustomAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : providers) {
            if (provider.supports(authentication.getClass())){
                return provider.authenticate(authentication);
            }
        }
        return null;
    }
}

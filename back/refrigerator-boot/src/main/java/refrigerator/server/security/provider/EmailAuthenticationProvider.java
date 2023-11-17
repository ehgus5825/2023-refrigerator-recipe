package refrigerator.server.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.security.exception.CustomAuthenticationException;
import refrigerator.server.security.token.EmailAuthenticationToken;


@RequiredArgsConstructor
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user.isEnabled() && passwordEncoder.matches(password, user.getPassword())){
            return new EmailAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        }
        throw new CustomAuthenticationException(AuthenticationExceptionType.NOT_EQUAL_PASSWORD);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(EmailAuthenticationToken.class);
    }
}

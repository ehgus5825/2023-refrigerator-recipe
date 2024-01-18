package refrigerator.server.security.authentication.application.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.security.common.exception.CustomAuthenticationException;


@RequiredArgsConstructor
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // 이건 CustomAuthenticationManager에서 안쓰이지 않나??
    // 초기 bean 주입으로 List에 provider에 들어가긴 하는데
    // 필터에서 얘를 호출하지 않으니..

    // 그래서 내생각인데 얘는 그냥 로그인때만 사용되는 것 같음.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // DB에서 user 정보를 받아서 UserDetails를 만듬
        UserDetails user = userDetailsService.loadUserByUsername(username);
        // UserDetails의 유효성 검사, 그리고 user의 password authentication으로 넘어온 password를 비교하고
        if (user.isEnabled() && passwordEncoder.matches(password, user.getPassword())){
            // EmailAuthenticationToken를 만들어서 반환
            return new EmailAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        }
        // 조건에 맞지 않는다면 예외 발생
        throw new CustomAuthenticationException(AuthenticationExceptionType.NOT_EQUAL_PASSWORD);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(EmailAuthenticationToken.class);
    }
}

package refrigerator.server.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import refrigerator.server.security.manager.CustomAuthenticationManager;
import refrigerator.server.security.provider.JwtAuthenticationProvider;
import refrigerator.server.security.authentication.application.service.JwtValidationService;
import refrigerator.server.security.provider.EmailAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

    private final JwtValidationService jwtValidationService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(){
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(jwtAuthenticationProvider());
        providers.add(emailAuthenticationProvider());
        return new CustomAuthenticationManager(providers);
    }

    @Bean(name = "emailAuthenticationProvider")
    @Primary
    public AuthenticationProvider emailAuthenticationProvider(){
        return new EmailAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean(name = "jwtAuthenticationProvider")
    public AuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider(jwtValidationService);
    }
}

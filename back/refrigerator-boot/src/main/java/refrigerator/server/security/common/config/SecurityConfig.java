package refrigerator.server.security.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refrigerator.server.security.authorization.filter.JwtAuthenticationFilter;
import refrigerator.server.security.authentication.application.oauth2.service.OAuth2DetailsServiceImpl;
import refrigerator.server.security.authentication.application.oauth2.handler.Oauth2FailureHandler;
import refrigerator.server.security.authentication.application.oauth2.handler.Oauth2SuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(AuthenticationManagerConfig.class)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final OAuth2DetailsServiceImpl principalOAuth2DetailsService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2FailureHandler oauth2FailureHandler;

    // 밑에 경로들 보니깐 좀 api 명세서랑 프론트 것 좀 보고 고쳐야할 듯

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        setOauth(http);
        setAuth(http);
        setIngredient(http);
        setMember(http);
        setWordCompletion(http);
        setDefault(http);
        setJwtFilter(http);
        return http.build();
    }

    // 일단 jwt 필터를 거침, 그다음에 Username~~ 필터를 거침

    // 근데 내가봤을 때 UsernamePasswordAuthenticationFilter 이건 그냥 안쓰는 느낌임
    // 로그인할 때 인증시 UsernamePasswordAuthenticationFilter 이걸 쓴다고 하는데
    // 밑에보면 login 접근시 접근 허용으로 해놨음.
    // 이미 jwt 필터를 거치지 않는 다는 말. 그래서 내 가설로는 JwtAuthenticationFilter 얘 하나만 쓰는 것 같음.
    private void setJwtFilter(HttpSecurity http) {
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class);
    }

    // permitAll() : 접근 승인
    // authenticated() : 인증된 사용자만 접근 허용

    // 일단 예외적인 것들 모두 제외하고 모든 api 요청에 authenticated을 걸어놨음
    // 그 밑에 것들은 나중에 확인해보자 (TODO)
    private void setDefault(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/**").authenticated()
                .and()
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void setWordCompletion(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/recipe/search/word-completion").permitAll()
                .mvcMatchers("/api/ingredient/search/word-completion").permitAll();
    }

    private void setIngredient(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/ingredients/search/unit").permitAll();
    }

    private void setMember(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/members/join").permitAll()
                .mvcMatchers("/api/members/email/check").permitAll();
    }

    // logout만 인증 권한 있는 사용자만 접근 허용
    // 나머지는 다 접근 허용
    private void setAuth(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/auth/logout").authenticated()
                .mvcMatchers("/api/auth/**").permitAll();
    }

    private void setOauth(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOAuth2DetailsService)
                .and()
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailureHandler);
    }
}
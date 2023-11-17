package refrigerator.server.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refrigerator.server.security.config.AuthenticationManagerConfig;
import refrigerator.server.security.filter.JwtAuthenticationFilter;
import refrigerator.server.security.user.OAuth2DetailsServiceImpl;
import refrigerator.server.security.handler.Oauth2FailureHandler;
import refrigerator.server.security.handler.Oauth2SuccessHandler;


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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        setOauth(http);
        setAuth(http);
        setComment(http);
        setIdentification(http);
        setMember(http);
        setRecipe(http);
        setWordCompletion(http);
        setDefault(http);
        setJwtFilter(http);
        return http.build();
    }

    private void setJwtFilter(HttpSecurity http) {
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class);
    }


    private SessionManagementConfigurer<HttpSecurity> setDefault(HttpSecurity http) throws Exception {
        return http
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

    private void setRecipe(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/recipe/{recipeID}/course").permitAll()
                .mvcMatchers("/api/recipe").permitAll()
                .mvcMatchers("/api/recipe/{recipeId}/ingredient/volume").permitAll()
                .mvcMatchers("/api/recipe/search/normal").permitAll()
                .mvcMatchers("/api/recipe/search/condition/food-type").permitAll()
                .mvcMatchers("/api/recipe/search/condition/category").permitAll()
                .mvcMatchers("/api/recipe/search/condition/recipe-type").permitAll()
                .mvcMatchers("/api/recipe/search/condition/difficulty").permitAll();
    }

    private void setMember(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/members/join").permitAll()
                .mvcMatchers("/api/members/password/find").permitAll()
                .mvcMatchers("/api/members/password").permitAll()
                .mvcMatchers("/api/members/email/check").permitAll()
                .mvcMatchers("/api/members/profile/list").permitAll();
    }

    private void setIdentification(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/identification/**").permitAll();
    }

    private void setComment(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/comments/{commentId}/delete").permitAll();
    }

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

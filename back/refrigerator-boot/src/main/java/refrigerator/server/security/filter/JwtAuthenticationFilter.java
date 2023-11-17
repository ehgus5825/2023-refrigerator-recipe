package refrigerator.server.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import refrigerator.server.security.authentication.application.service.MakeResponseMessageService;
import refrigerator.server.security.config.JsonWebTokenKey;
import refrigerator.server.security.exception.JsonWebTokenException;
import refrigerator.server.security.token.JwtAuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getAccessToken((HttpServletRequest) request);
        try{
            Authentication authenticate = authenticationManager.authenticate(new JwtAuthenticationToken(token));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            chain.doFilter(request, response);
        }catch (JsonWebTokenException e){
            MakeResponseMessageService.makeResponseMessage((HttpServletResponse) response, e);
        }
    }

    private String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(JsonWebTokenKey.BEARER_TYPE + " ")){
            return token.substring(7);
        }
        return null;
    }

}

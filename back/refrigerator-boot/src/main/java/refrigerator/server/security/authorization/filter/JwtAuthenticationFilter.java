package refrigerator.server.security.authorization.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import refrigerator.server.security.common.jwt.JsonWebTokenKey;
import refrigerator.server.security.common.exception.JsonWebTokenException;
import refrigerator.server.security.authorization.provider.JwtAuthenticationToken;

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
            // 이 밑에 두줄이 굉장히 중요함. 이 두줄에서 걸려버리면 controller로 접근이 불가함
            // manager에 JwtAuthenticationToken가 삽입됨
            Authentication authenticate = authenticationManager.authenticate(new JwtAuthenticationToken(token));
            // 여기서 만들어진 authenticate을 토대로 우리가 controller에서 회원 이메일을 받을 수 있었음
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            chain.doFilter(request, response);
        }catch (JsonWebTokenException e){
            MakeResponseMessageService.makeResponseMessage((HttpServletResponse) response, e);
        }
    }

    // 토큰 자르기
    private String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(JsonWebTokenKey.BEARER_TYPE + " ")){
            return token.substring(7);
        }
        return null;
    }

}

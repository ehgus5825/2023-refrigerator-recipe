package refrigerator.server.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2FailureHandler implements AuthenticationFailureHandler {

    @Value("${front.domain}")
    private String frontDomain;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OAuth2AuthenticationException e = (OAuth2AuthenticationException) exception;
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.sendRedirect(frontDomain + "/member/fail?error="
                + e.getError().getErrorCode()
                + "&message="
                + e.getError().getDescription());
    }
}

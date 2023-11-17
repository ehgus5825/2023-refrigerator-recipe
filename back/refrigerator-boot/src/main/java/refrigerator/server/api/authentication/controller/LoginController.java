package refrigerator.server.api.authentication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.security.authentication.application.TokenDto;
import refrigerator.server.security.authentication.application.usecase.LoginUseCase;
import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.api.authentication.dto.LoginRequestDTO;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class LoginController {

    private final LoginUseCase loginUseCase;
    private final String oauthPassword;
    private final String frontDomain;

    public LoginController(LoginUseCase loginUseCase,
                           @Value("${oauth.password}") String oauthPassword,
                           @Value("${front.domain}") String frontDomain) {
        this.loginUseCase = loginUseCase;
        this.oauthPassword = oauthPassword;
        this.frontDomain = frontDomain;
    }

    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto loginByEmail(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        return login(request.getEmail(), request.getPassword(), response);
    }

    @GetMapping("/api/auth/login/oauth")
    @ResponseStatus(HttpStatus.CREATED)
    public void loginByOAuth2(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
        try{
            TokenDto token = login(email, oauthPassword, response);
            response.sendRedirect(frontDomain + "/member/success?token=" + token.getAccessToken());
        } catch (BusinessException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect(frontDomain + "/member/fail?errorCode="
                    + e.getBasicExceptionType().getErrorCode());
        }
    }

    private TokenDto login(String email, String password, HttpServletResponse response) {
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        TokenDto token = loginUseCase.login(email, password);
        response.addCookie(refreshTokenCookie.create(token.getRefreshToken()));
        token.removeRefreshToken();
        return token;
    }
}

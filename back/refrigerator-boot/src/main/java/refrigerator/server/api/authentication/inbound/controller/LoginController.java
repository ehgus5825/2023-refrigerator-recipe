package refrigerator.server.api.authentication.inbound.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.application.usecase.LoginUseCase;
import refrigerator.server.api.authentication.application.dto.TokenDto;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.api.authentication.inbound.dto.request.LoginRequestDTO;
import refrigerator.server.api.authentication.inbound.cookie.RefreshTokenCookie;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static refrigerator.back.member.exception.MemberExceptionType.*;
import static refrigerator.server.api.global.common.InputDataFormatCheck.*;
import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;

@RestController
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
    public TokenDto loginByEmail(@Valid @RequestBody LoginRequestDTO request,
                                 BindingResult bindingResult,
                                 HttpServletResponse response) {

        multiCheck(bindingResult);

        return login(request.getEmail(), request.getPassword(), response);
    }

    // TODO : disable?

    @GetMapping("/api/auth/login/oauth")
    @ResponseStatus(HttpStatus.CREATED)
    public void loginByOAuth2(@RequestParam("email") String email,
                              HttpServletResponse response) throws IOException {

        patternCheck(EMAIL_REGEX, email, INCORRECT_EMAIL_FORMAT);

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

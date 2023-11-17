package refrigerator.server.api.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.security.authentication.application.usecase.RestrictAccessUseCase;
import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogoutController {
    private final RestrictAccessUseCase restrictAccessUseCase;

    public LogoutController(RestrictAccessUseCase restrictAccessUseCase) {
        this.restrictAccessUseCase = restrictAccessUseCase;
    }

    @GetMapping("/api/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response){
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        restrictAccessUseCase.restrictAccessToTokens(refreshToken);
        response.addCookie(refreshTokenCookie.delete());
    }
}

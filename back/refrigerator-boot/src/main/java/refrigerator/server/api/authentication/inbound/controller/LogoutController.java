package refrigerator.server.api.authentication.inbound.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.authentication.application.usecase.RestrictAccessUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LogoutController {
    private final RestrictAccessUseCase restrictAccessUseCase;

    @GetMapping("/api/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response){

        response.addCookie(restrictAccessUseCase.restrictAccessToTokens(request.getCookies()));
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
}

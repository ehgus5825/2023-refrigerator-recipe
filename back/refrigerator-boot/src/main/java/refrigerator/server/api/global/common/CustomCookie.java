package refrigerator.server.api.global.common;

import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface CustomCookie {
    Cookie create(String value);
    void delete(HttpServletResponse response);
    Cookie get(Cookie[] cookies);

    boolean isValid(JsonWebTokenUseCase provider, Cookie target);

}

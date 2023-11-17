package refrigerator.server.api.authentication.cookie;

import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.security.exception.JsonWebTokenException;

import javax.servlet.http.Cookie;
import java.util.Arrays;


public class RefreshTokenCookie {

    private final String cookieName = "Refresh-Token";
    private final String cookiePath = "/api/";
    private final int cookieMaxAge = 3600 * 24 * 30; // 30일

    public Cookie create(String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieMaxAge); // 30 일
        return cookie;
    }

    public Cookie delete() {
        Cookie cookie = new Cookie(cookieName, "deleted");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(0);
        return cookie;
    }

    public Cookie get(Cookie[] cookies) {
        if (cookies == null){
            throw new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_COOKIE);
        }
        return Arrays.stream(cookies)
                .filter(this::isValid)
                .findAny()
                .orElseThrow(() -> new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_TOKEN));
    }

    private boolean isValid(Cookie target){
        return target.getName().equals(cookieName);
    }

}

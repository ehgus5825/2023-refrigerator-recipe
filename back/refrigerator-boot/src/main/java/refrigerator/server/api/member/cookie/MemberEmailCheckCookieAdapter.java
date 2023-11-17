package refrigerator.server.api.member.cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.security.exception.JsonWebTokenException;

import javax.servlet.http.Cookie;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MemberEmailCheckCookieAdapter {
    private final String cookieName = "Email-Check";
    private final String cookiePath = "/api/members/join";
    private final int cookieMaxAge = 60 * 30;

    public Cookie create(String email){
        Cookie cookie = new Cookie(cookieName, email);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieMaxAge); // 30분
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    public boolean isExist(Cookie[] cookies, String value){
        if (cookies == null){
            throw new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_COOKIE);
        }
        return Arrays.stream(cookies)
                .anyMatch(cookie -> isValid(cookie, value));
    }

    public Cookie delete(){
        Cookie cookie = new Cookie(cookieName, "delete");
        cookie.setPath("/api/members/join");
        cookie.setMaxAge(0); // 30분
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    private boolean isValid(Cookie cookie, String email){
        return cookie.getName().equals(cookieName)
                && cookie.getValue().equals(email);
    }
}

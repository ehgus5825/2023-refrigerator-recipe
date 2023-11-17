package refrigerator.server.api.member.cookie;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class MemberCookieAdapter {

    private final Cookie[] cookies;

    public MemberCookieAdapter(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public void isVerified(HttpServletResponse response, String email){
        String cookieName = "Verified-User";
        if (cookies != null){
            boolean result = Arrays.stream(cookies)
                    .anyMatch(cookie -> cookie.getName().equals(cookieName) && cookie.getValue().equals(email));
            if (result){
                Cookie cookie = new Cookie(cookieName, "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return;
            }
        }
        throw new BusinessException(MemberExceptionType.NOT_COMPLETED_IDENTIFICATION);
    }
}

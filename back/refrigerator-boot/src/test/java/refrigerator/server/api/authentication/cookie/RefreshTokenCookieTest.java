package refrigerator.server.api.authentication.cookie;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.security.exception.JsonWebTokenException;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RefreshTokenCookieTest {

    private RefreshTokenCookie refreshTokenCookie;

    @BeforeEach
    void init(){
        refreshTokenCookie = new RefreshTokenCookie();
    }

    @Test
    @DisplayName("refresh token cookie 찾는 테스트")
    void get() {
        Cookie[] cookies = {refreshTokenCookie.create("token")};
        assertDoesNotThrow(() -> refreshTokenCookie.get(cookies));
    }

    @Test
    @DisplayName("refresh token cookie 찾기 실패 테스트")
    void getFailTest() {
        Cookie[] cookies = {new Cookie("wrong", "deleted")};
        assertThrows(JsonWebTokenException.class, () -> {
            try{
                refreshTokenCookie.get(cookies);
            } catch (JsonWebTokenException e) {
                assertEquals(AuthenticationExceptionType.NOT_FOUND_TOKEN, e.getBasicExceptionType());
                throw e;
            }
        });
    }

}
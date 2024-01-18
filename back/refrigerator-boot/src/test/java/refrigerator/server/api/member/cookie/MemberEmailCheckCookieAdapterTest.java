package refrigerator.server.api.member.cookie;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.security.common.exception.JsonWebTokenException;

import javax.servlet.http.Cookie;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberEmailCheckCookieAdapterTest {

    private MemberEmailCheckCookieAdapter adapter;

    @BeforeEach
    void init(){
        adapter = new MemberEmailCheckCookieAdapter();
    }

    @Test
    @DisplayName("중복 확인 쿠키가 존재하는 경우")
    void isExist() {
        String email = "email";
        Cookie[] cookies = {adapter.create(email)};
        assertDoesNotThrow(() -> adapter.isExist(cookies, email));
    }

    @Test
    @DisplayName("중복 확인 쿠키가 존재하지 않는 경우 (cookies가 null인 경우")
    void isExistFailTest() {
        String email = "email";
        assertThrows(JsonWebTokenException.class, () -> {
            try{
                adapter.isExist(null, email);
            } catch (BusinessException e){
                assertEquals(e.getBasicExceptionType(), AuthenticationExceptionType.NOT_FOUND_COOKIE);
                throw e;
            }
        });
    }

}
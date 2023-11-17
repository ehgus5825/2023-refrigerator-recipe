package refrigerator.server.api.member.cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("중복 확인 쿠키가 존재하지 않는 경우")
    void isExistFailTest() {
        String email = "email";
        Cookie[] cookies = {adapter.delete()};
        assertThrows(BusinessException.class,
                () -> {
            try{
                adapter.isExist(cookies, email);
            } catch (BusinessException e){
                assertEquals(e.getBasicExceptionType(), MemberExceptionType.NOT_COMPLETED_EMAIL_DUPLICATION_CHECK);
                throw e;
            }
        });
    }

}
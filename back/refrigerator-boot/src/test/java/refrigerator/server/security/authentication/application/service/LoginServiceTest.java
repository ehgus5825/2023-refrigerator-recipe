package refrigerator.server.security.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.authentication.application.dto.TokenDto;
import refrigerator.server.api.authentication.application.usecase.LoginUseCase;
import refrigerator.server.security.common.exception.CustomAuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class LoginServiceTest {

    @Autowired LoginUseCase loginUseCase;

    @Autowired JoinUseCase joinUseCase;

    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() {

        joinUseCase.join("mstest111@gmail.com", passwordEncoder.encode("password123!"), "nick");

        // given
        String email = "mstest111@gmail.com";
        String password = "password123!";
        // when
        TokenDto token = loginUseCase.login(email, password);
        // then
        Assertions.assertNotNull(token.getAccessToken());
        Assertions.assertNotNull(token.getRefreshToken());
    }

    @Test
    @DisplayName("로그인 실패 테스트 -> 존재하지 않는 회원")
    void loginFailTest1(){
        // given
        String email = "worngemail@gmail.com";
        String password = "password";

        // when & then
        assertThrows(BusinessException.class, () -> {
            try{
                loginUseCase.login(email, password);
            } catch (BusinessException e){
                log.info("error message={}", e.getMessage());
                assertEquals(MemberExceptionType.NOT_FOUND_MEMBER, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("로그인 실패 테스트 -> 비밀번호가 틀렸을 때")
    void loginFailTest2(){

        joinUseCase.join("mstest123@gmail.com", passwordEncoder.encode("password123!"), "nick");

        // given
        String email = "mstest123@gmail.com";
        String password = "password!";
        // when & then
        assertThrows(CustomAuthenticationException.class, () -> {
            try{
                loginUseCase.login(email, password);
            } catch (CustomAuthenticationException e){
                log.info("error message = {}", e.getMessage());
                assertEquals(AuthenticationExceptionType.NOT_EQUAL_PASSWORD, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}
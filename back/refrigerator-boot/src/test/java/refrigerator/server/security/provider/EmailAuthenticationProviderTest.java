package refrigerator.server.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.security.exception.CustomAuthenticationException;
import refrigerator.server.security.token.EmailAuthenticationToken;
import refrigerator.server.security.user.User;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EmailAuthenticationProviderTest {

    @InjectMocks EmailAuthenticationProvider provider;
    @Mock UserDetailsService service;
    @Mock PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 서비스에서 받은 객체를 정상 인증")
    void authenticateSuccessTest() {
        //given
        /* Domain -> Service : DB 에서 회원 객체 전달 */
        BDDMockito.given(service.loadUserByUsername("username"))
                .willReturn(new User(new UserDto("username", "encodedPassword", "STEADY_STATUS")));
        /* DB 에서 가져온 회원 객체 비밀번호(encodedPassword)와 로그인 서비스에서 받은 Authentication 객체의 password 를 비교 */
        BDDMockito.given(passwordEncoder.matches("password", "encodedPassword"))
                .willReturn(true);
        Authentication authentication = new EmailAuthenticationToken("username", "password");
        Assertions.assertFalse(authentication.isAuthenticated());

        // when && then
        Authentication result = provider.authenticate(authentication);
        Assertions.assertTrue(result.isAuthenticated());
    }

    @Test
    @DisplayName("로그인 서비스에서 받은 객체 인증 실패 -> 탈퇴한 회원")
    void authenticateFailTest1() {
        BDDMockito.given(service.loadUserByUsername("username"))
                .willReturn(new User(new UserDto("username", "encodedPassword", MemberStatus.LEAVE_STATUS.toString())));
        Authentication authentication = new EmailAuthenticationToken("username", "password");
        Assertions.assertFalse(authentication.isAuthenticated());
        Assertions.assertThrows(BusinessException.class, () -> {
            try{
                provider.authenticate(authentication);
            } catch (BusinessException e){
                log.info("error message = {}", e.getMessage());
                assertEquals(MemberExceptionType.WITHDRAWN_MEMBER, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("로그인 서비스에서 받은 객체 인증 실패 -> 차단된 회원")
    void authenticateFailTest2() {
        BDDMockito.given(service.loadUserByUsername("username"))
                .willReturn(new User(new UserDto("username", "encodedPassword", MemberStatus.BLOCK_STATUS.toString())));
        Authentication authentication = new EmailAuthenticationToken("username", "password");
        Assertions.assertFalse(authentication.isAuthenticated());
        Assertions.assertThrows(BusinessException.class, () -> {
            try{
                provider.authenticate(authentication);
            } catch (BusinessException e){
                log.info("error message = {}", e.getMessage());
                assertEquals(MemberExceptionType.BLOCKED_MEMBER, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("로그인 서비스에서 받은 객체 인증 실패 -> 휴면 상태 회원")
    void authenticateFailTest3() {
        // given
        BDDMockito.given(service.loadUserByUsername("username"))
                .willReturn(new User(new UserDto("username", "encodedPassword", MemberStatus.DORMANT_STATUS.toString())));
        Authentication authentication = new EmailAuthenticationToken("username", "password");

        // when && then
        Assertions.assertFalse(authentication.isAuthenticated());
        Assertions.assertThrows(BusinessException.class, () -> {
            try{
                provider.authenticate(authentication);
            } catch (BusinessException e){
                log.info("error message = {}", e.getMessage());
                assertEquals(MemberExceptionType.DORMANT_MEMBER, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("로그인 서비스에서 받은 객체 인증 실패 -> 비밀번호 불일치")
    void authenticateFailTest4() {
        // given
        BDDMockito.given(service.loadUserByUsername("username"))
                .willReturn(new User(new UserDto("username", "encodedPassword", MemberStatus.STEADY_STATUS.toString())));
        BDDMockito.given(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .willReturn(false);
        Authentication authentication = new EmailAuthenticationToken("username", "wrongPassword");
        Assertions.assertFalse(authentication.isAuthenticated());

        // when & then
        Assertions.assertThrows(CustomAuthenticationException.class, () -> {
            try{
                provider.authenticate(authentication);
            } catch (CustomAuthenticationException e){
                log.info("error message = {}", e.getMessage());
                assertEquals(AuthenticationExceptionType.NOT_EQUAL_PASSWORD, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}
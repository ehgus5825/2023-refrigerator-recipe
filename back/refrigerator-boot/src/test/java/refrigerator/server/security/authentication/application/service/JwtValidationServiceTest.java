package refrigerator.server.security.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.server.security.authentication.application.TokenStatus;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;
import refrigerator.server.security.exception.JsonWebTokenException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class JwtValidationServiceTest {

    @InjectMocks JwtValidationService service;
    @Mock JsonWebTokenUseCase jsonWebTokenUseCase;
    @Mock RefreshTokenPort refreshTokenPort;

    @Test
    @DisplayName("토큰 유효성 검사 성공 테스트")
    void toValidateSuccessTest() {
        given(jsonWebTokenUseCase.getTokenStatus("token"))
                .willReturn(TokenStatus.PASS);
        given(jsonWebTokenUseCase.parseClaims("token"))
                .willReturn(new ClaimsDto("email", "STEADY_STATUS", null));
        ClaimsDto result = service.checkValidation("token");
        assertEquals("email", result.getEmail());
        assertEquals("STEADY_STATUS", result.getRole());
    }

    @Test
    @DisplayName("RefreshToken 유효성 검사 성공 테스트 -> 접근 가능한 RefreshToken 일 때")
    void checkValidationByRefreshTokenSuccessTest(){
        given(refreshTokenPort.getToken("refreshToken"))
                .willReturn(Optional.of(new RefreshToken("id", "refreshToken", true)));
        given(jsonWebTokenUseCase.parseClaims("refreshToken"))
                .willReturn(new ClaimsDto("email", "STEADY_STATUS", null));
        ClaimsDto result = service.checkValidationByRefreshToken("refreshToken");
        assertEquals("email", result.getEmail());
        assertEquals("STEADY_STATUS", result.getRole());
    }

    @Test
    @DisplayName("RefreshToken 유효성 검사 실패 테스트 -> 접근 불가능한 RefreshToken 일 때")
    void checkValidationByRefreshTokenFailTest(){
        given(refreshTokenPort.getToken("refreshToken"))
                .willReturn(Optional.of(new RefreshToken("id", "refreshToken", false)));
        assertThrows(JsonWebTokenException.class,
                () -> service.checkValidationByRefreshToken("refreshToken"));
    }

    @Test
    @DisplayName("토큰 상태 확인 테스트 -> 만료된 토큰일 때")
    void checkTokenStatusExceptionTest1(){
        given(jsonWebTokenUseCase.getTokenStatus("token"))
                .willReturn(TokenStatus.EXPIRED);
        Assertions.assertThrows(
                JsonWebTokenException.class,
                () -> service.checkValidation("token"));
    }

    @Test
    @DisplayName("토큰 상태 확인 테스트 -> 잘못된 토큰일 때")
    void checkTokenStatusExceptionTest2(){
        given(jsonWebTokenUseCase.getTokenStatus("token"))
                .willReturn(TokenStatus.WRONG);
        Assertions.assertThrows(
                JsonWebTokenException.class,
                () -> service.checkValidation("token"));
    }

    @Test
    @DisplayName("권한 확인 테스트 -> 권한이 빈 문자열일 때")
    void checkAuthorityExceptionTest1() {
        given(jsonWebTokenUseCase.getTokenStatus("token"))
                .willReturn(TokenStatus.PASS);
        given(jsonWebTokenUseCase.parseClaims("token"))
                .willReturn(new ClaimsDto("email", "", null));
        Assertions.assertThrows(
                JsonWebTokenException.class,
                () -> service.checkValidation("token"));
    }

    @Test
    @DisplayName("권한 확인 테스트 -> 권한이 null 일 때")
    void checkAuthorityExceptionTest2() {
        given(jsonWebTokenUseCase.getTokenStatus("token"))
                .willReturn(TokenStatus.PASS);
        given(jsonWebTokenUseCase.parseClaims("token"))
                .willReturn(new ClaimsDto("email", null, null));
        Assertions.assertThrows(
                JsonWebTokenException.class,
                () -> service.checkValidation("token"));
    }

    @Test
    @DisplayName("권한 확인 테스트 -> 잘못된 권한일 때")
    void checkAuthorityExceptionTest3() {
        given(jsonWebTokenUseCase.getTokenStatus("token"))
                .willReturn(TokenStatus.PASS);
        given(jsonWebTokenUseCase.parseClaims("token"))
                .willReturn(new ClaimsDto("email", "role", null));
        Assertions.assertThrows(
                JsonWebTokenException.class,
                () -> service.checkValidation("token"));
    }
}
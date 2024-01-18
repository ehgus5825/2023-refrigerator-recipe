package refrigerator.server.security.authorization;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.server.security.common.jwt.TokenStatus;
import refrigerator.server.security.common.dto.ClaimsDto;
import refrigerator.server.security.authorization.provider.JwtValidationService;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;
import refrigerator.server.security.common.exception.JsonWebTokenException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class JwtValidationServiceTest {

    @InjectMocks JwtValidationService service;
    @Mock JsonWebTokenUseCase jsonWebTokenUseCase;

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
package refrigerator.server.security.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.authentication.outbound.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.security.authentication.application.usecase.RestrictAccessUseCase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RestrictAccessByTokenServiceTest {

    @Autowired
    RestrictAccessUseCase restrictAccessUseCase;
    @Autowired RefreshTokenRepository refreshTokenRepository;

    @AfterEach
    void clear(){
        refreshTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void logoutSuccessTest() {
        String refreshToken = "refreshToken";
        refreshTokenRepository.save(new RefreshToken("id", refreshToken, true));
        restrictAccessUseCase.restrictAccessToTokens(refreshToken);
        Optional<RefreshToken> result = refreshTokenRepository.findRefreshTokenByToken(refreshToken);
        assertTrue(result.isPresent());
        assertFalse(result.get().isValidated());
    }

    @Test
    @DisplayName("로그아웃 실패 테스트 -> 이미 로그아웃한 사용자이거나 접근 불가능한 회원인 경우")
    void logoutFailTest() {
        String refreshToken = "refreshToken";
        refreshTokenRepository.save(new RefreshToken("id", refreshToken, false));
        assertThrows(BusinessException.class, () -> {
            try{
                restrictAccessUseCase.restrictAccessToTokens(refreshToken);
            }catch (BusinessException e){
                log.info("error message = {}", e.getMessage());
                assertEquals(AuthenticationExceptionType.FAIL_ACCESS_BY_TOKEN, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}
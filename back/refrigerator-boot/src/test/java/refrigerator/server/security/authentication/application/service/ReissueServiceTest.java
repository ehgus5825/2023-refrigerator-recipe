package refrigerator.server.security.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.outbound.repository.redis.RefreshTokenRepository;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.server.api.authentication.application.dto.TokenDto;
import refrigerator.server.api.authentication.application.service.ReissueService;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;
import refrigerator.server.api.authentication.application.usecase.ReissueUseCase;
import refrigerator.server.security.common.exception.JsonWebTokenException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReissueServiceTest {

    @Autowired ReissueUseCase reissueUseCase;

    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;

    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Autowired RefreshTokenPort refreshTokenPort;

    @Autowired
    ReissueService reissueService;

    @AfterEach
    void clear(){
        refreshTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰 재발급 성공 테스트")
    void reissueSuccessTest() {
        // given
        String refreshToken = jsonWebTokenUseCase.createToken("username", MemberStatus.STEADY_STATUS.toString(), 1000 * 60);
        refreshTokenRepository.save(new RefreshToken("username", refreshToken, true));
        // when
        TokenDto reissue = reissueUseCase.reissue(refreshToken);
        // then
        assertNotNull(reissue.getAccessToken());
    }

    @Test
    @DisplayName("토큰 재발급 실패 테스트 -> 무효화된 토큰으로 재발급을 시도했을 때")
    void reissueFailTest1() {
        // given
        String refreshToken = jsonWebTokenUseCase.createToken("username", MemberStatus.STEADY_STATUS.toString(), 1000 * 60);
        refreshTokenRepository.save(new RefreshToken("username", refreshToken, false));
        // when && then
        assertThrows(JsonWebTokenException.class, () -> {
            try{
                reissueUseCase.reissue(refreshToken);
            } catch (JsonWebTokenException e){
                log.info("error message={}", e.getMessage());
                assertEquals(AuthenticationExceptionType.FAIL_ACCESS_BY_TOKEN, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("토큰 재발급 실패 테스트 -> 토큰을 찾을 수 없을 때 (토큰이 만료됨)")
    void reissueFailTest2() {
        // when && then
        assertThrows(JsonWebTokenException.class, () -> {
            try {
                reissueUseCase.reissue("refreshToken");
            } catch (JsonWebTokenException e) {
                log.info("error message={}", e.getMessage());
                assertEquals(AuthenticationExceptionType.NOT_FOUND_TOKEN, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}
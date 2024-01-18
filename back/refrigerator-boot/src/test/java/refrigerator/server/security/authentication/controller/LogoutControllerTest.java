package refrigerator.server.security.authentication.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.outbound.repository.redis.RefreshTokenRepository;
import refrigerator.server.api.authentication.inbound.cookie.RefreshTokenCookie;
import refrigerator.server.security.common.TestTokenService;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class LogoutControllerTest {

    @Autowired MockMvc mvc;
    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;

    @Autowired RefreshTokenPort refreshTokenPort;

    @Autowired RefreshTokenRepository refreshTokenRepository;

    @AfterEach
    void clear(){
        refreshTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void logoutSuccessTest() throws Exception {
        String refreshToken = TestTokenService.getRefreshToken(jsonWebTokenUseCase, refreshTokenPort, true);
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        mvc.perform(MockMvcRequestBuilders.get("/api/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
                .cookie(refreshTokenCookie.create(refreshToken)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 실패 테스트 -> 유효하지 않은 토큰")
    void logoutFailTest() throws Exception {
        String refreshToken = "refreshToken";
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        mvc.perform(MockMvcRequestBuilders.get("/api/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
                        .cookie(refreshTokenCookie.create(refreshToken)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
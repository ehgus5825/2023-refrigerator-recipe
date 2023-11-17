package refrigerator.server.api.authentication.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;
import refrigerator.server.config.ExcludeSecurityConfiguration;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;
import refrigerator.server.security.authentication.application.usecase.RestrictAccessUseCase;
import refrigerator.server.security.exception.JsonWebTokenException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class LogoutControllerTest {

    @Autowired MockMvc mvc;
    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void logoutSuccessTest() throws Exception {
        String refreshToken = "refreshToken";
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        mvc.perform(MockMvcRequestBuilders.get("/api/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
                .cookie(refreshTokenCookie.create(refreshToken)))
                .andExpect(cookie().maxAge("Refresh-Token", 0))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 실패 테스트 -> 유효하지 않은 토큰")
    void logoutFailTest() throws Exception {
        String refreshToken = "refreshToken";
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        mvc.perform(MockMvcRequestBuilders.get("/api/auth/logout")
                        .cookie(refreshTokenCookie.create(refreshToken)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
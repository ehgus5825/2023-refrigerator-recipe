package refrigerator.server.api.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import refrigerator.back.authentication.outbound.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;
import refrigerator.server.api.authentication.dto.TemporaryAccessTokenRequestDTO;
import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TokenIssueControllerTest {

    @Autowired MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;
    @Autowired RefreshTokenRepository refreshTokenRepository;

    @AfterEach
    void clear(){
        refreshTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰 재발급 성공 테스트")
    void reissueSuccessTest() throws Exception {
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String email = "mstest102@gmail.com";
        String token = jsonWebTokenUseCase.createToken(email, "STEADY_STATUS", 1000 * 60);
        refreshTokenRepository.save(new RefreshToken(email, token, true));
        mvc.perform(post("/api/auth/reissue")
                        .cookie(refreshTokenCookie.create(token)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").isString())
                .andDo(print());
    }

    @Test
    @DisplayName("토큰 재발급 실패 테스트 -> 무효화된 토큰일 경우")
    void reissueFailTest() throws Exception {
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String email = "mstest102@gmail.com";
        String token = jsonWebTokenUseCase.createToken(email, "STEADY_STATUS", 1000 * 60);
        refreshTokenRepository.save(new RefreshToken(email, token, false));
        mvc.perform(post("/api/auth/reissue")
                        .cookie(refreshTokenCookie.create(token)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 찾기를 위한 임시 토큰 발급 성공 테스트")
    void issueTemporaryTokenSuccessTest() throws Exception{
        String email = "mstest102@gmail.com";
        mvc.perform(post("/api/auth/issue/temporary-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new TemporaryAccessTokenRequestDTO(email))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
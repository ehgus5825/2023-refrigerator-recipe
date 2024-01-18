package refrigerator.server.security.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.authentication.outbound.repository.redis.RefreshTokenRepository;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.server.api.authentication.inbound.cookie.RefreshTokenCookie;
import refrigerator.server.api.authentication.inbound.dto.request.TemporaryAccessTokenRequestDTO;
import refrigerator.server.security.common.TestTokenService;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TokenIssueControllerTest {

    @Autowired MockMvc mvc;

    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;

    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Autowired RefreshTokenPort refreshTokenPort;

    @Autowired JoinUseCase joinUseCase;

    @Autowired PasswordEncoder passwordEncoder;

    ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void clear(){
        refreshTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰 재발급 성공 테스트")
    void reissueSuccessTest() throws Exception {

        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String token = TestTokenService.getRefreshToken(jsonWebTokenUseCase, refreshTokenPort, true);

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
        String token = TestTokenService.getRefreshToken(jsonWebTokenUseCase, refreshTokenPort, false);

        mvc.perform(post("/api/auth/reissue")
                        .cookie(refreshTokenCookie.create(token)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 찾기를 위한 임시 토큰 발급 성공 테스트")
    void issueTemporaryTokenSuccessTest() throws Exception{
        String email = "mstest190@gmail.com";

        joinUseCase.join("mstest190@gmail.com", passwordEncoder.encode("password123!"), "nick");

        mvc.perform(post("/api/auth/issue/temporary-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new TemporaryAccessTokenRequestDTO(email))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 찾기를 위한 임시 토큰 발급 실패 테스트 => 이메일 입력 형식 오류")
    void issueTemporaryTokenSuccessTest2() throws Exception{
        String email = "mstest102";
        mvc.perform(post("/api/auth/issue/temporary-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new TemporaryAccessTokenRequestDTO(email))))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
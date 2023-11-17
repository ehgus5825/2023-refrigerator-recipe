package refrigerator.server.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.CheckEmailUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.EmailCheckRequestDto;
import refrigerator.server.config.ExcludeSecurityConfiguration;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmailCheckControllerTest {

    @Autowired MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("이메일 중복 확인")
    void duplicateCheckEmail() throws Exception {
        // given
        String email = "email123@gmail.com";
        EmailCheckRequestDto requestDto = new EmailCheckRequestDto(email);
        String json = objectMapper.writeValueAsString(requestDto);
        String cookieName = "cookieName";
        String cookieValue = "cookieValue";
        // when & then
        mvc.perform(post("/api/members/email/duplicate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(cookie().value(cookieName, cookieValue)
        ).andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 -> 중복된 이메일")
    void duplicateCheckEmailFailTest1() throws Exception {
        // given
        String email = "email123@gmail.com";
        EmailCheckRequestDto requestDto = new EmailCheckRequestDto(email);
        String json = objectMapper.writeValueAsString(requestDto);
        String cookieName = "cookieName";
        String cookieValue = "cookieValue";
        // when & then
        mvc.perform(post("/api/members/email/duplicate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()
        ).andExpect(cookie().doesNotExist(cookieName)
        ).andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 -> 올바르지 않은 이메일 형식")
    void duplicateCheckEmailFailTest2() throws Exception {
        // given
        String wrongEmail = "wrongEmail";
        EmailCheckRequestDto requestDto = new EmailCheckRequestDto(wrongEmail);
        String json = objectMapper.writeValueAsString(requestDto);
        String cookieName = "cookieName";
        // when & then
        mvc.perform(post("/api/members/email/duplicate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()
        ).andExpect(cookie().doesNotExist(cookieName)
        ).andExpect(jsonPath("$.code").isString()
        ).andExpect(jsonPath("$.message").isString()
        ).andDo(print());
    }
}
package refrigerator.server.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.JoinRequestDto;
import refrigerator.server.config.ExcludeSecurityConfiguration;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberJoinControllerTest {

    @Autowired MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void joinMember() throws Exception {
        // given
        String encodePassword = "encodePassword";
        String email = "email123@gmail.com";
        String password = "password123!";
        String nickname = "닉네임";
        Cookie cookie = new Cookie("cookieName", "cookieValue");
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);
        String json = objectMapper.writeValueAsString(requestDto);
        // when
        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(cookie().value("cookieName", "deleted")
        ).andExpect(status().isCreated()
        ).andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 입력 형식 오류")
    @Disabled
    void joinMemberFailTest1() throws Exception {
        // given
        String email = "email123";
        String password = "password";
        String nickname = "nickname";
        Cookie cookie = new Cookie("cookieName", "cookieValue");
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);
        String json = objectMapper.writeValueAsString(requestDto);
        // when
        // TODO : 입력 유효성 검사 독립적인 에러를 발생시키도록 수정하기
        MemberExceptionType exceptionType = MemberExceptionType.INCORRECT_EMAIL_FORMAT;
        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is4xxClientError()
        ).andExpect(jsonPath("$.code").value(exceptionType.toString())
        ).andExpect(jsonPath("$.message").value(exceptionType.getMessage())
        ).andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 이메일 중복 미확인")
    void joinMemberFailTest2() throws Exception {
        // given
        String email = "email123@gmail.com";
        String password = "password123!";
        String nickname = "닉네임";
        Cookie cookie = new Cookie("cookieName", "cookieValue");
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);
        String json = objectMapper.writeValueAsString(requestDto);
        // when
        MemberExceptionType exceptionType = MemberExceptionType.NOT_COMPLETED_EMAIL_DUPLICATION_CHECK;
        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is4xxClientError()
        ).andExpect(jsonPath("$.code").value(exceptionType.toString())
        ).andExpect(jsonPath("$.message").value(exceptionType.getMessage())
        ).andDo(print());
    }
}
package refrigerator.server.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.member.dto.request.EmailCheckRequestDto;
import refrigerator.server.api.member.dto.request.JoinRequestDto;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JoinControllerTest {

    @Autowired MockMvc mvc;

    @Autowired JoinUseCase joinUseCase;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("회원 가입 성공 테스트")
    void joinMember() throws Exception {
        // given
        String email = "email125@gmail.com";
        String password = "password123!";
        String nickname = "닉네임";
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);

        Cookie cookie = new Cookie("Email-Check", "email125@gmail.com");

        String json = objectMapper.writeValueAsString(requestDto);
        // when
        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(cookie().value("Email-Check", "delete")
        ).andExpect(status().isCreated()
        ).andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 이메일 입력 형식 오류")
    void joinMemberFailTest1_1() throws Exception {
        // given
        String email = "email123";
        String password = "password123!!";
        String nickname = "nickname";
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);

        Cookie cookie = new Cookie("Email-Check", "email123@email.com");

        String json = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is4xxClientError()
        ).andExpect(jsonPath("$.code").value("INCORRECT_DATA_FORMAT")
        ).andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 패스워드 입력 형식 오류")
    void joinMemberFailTest1_2() throws Exception {
        // given
        String email = "email123@email.com";
        String password = "password";
        String nickname = "nickname";
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);

        Cookie cookie = new Cookie("Email-Check", "email123@email.com");

        String json = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is4xxClientError()
        ).andExpect(jsonPath("$.code").value("INCORRECT_DATA_FORMAT")
        ).andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 닉네임 입력 형식 오류")
    void joinMemberFailTest1_3() throws Exception {
        // given
        String email = "email123@email.com";
        String password = "password123!!";
        String nickname = "nick닉넴";
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);

        Cookie cookie = new Cookie("Email-Check", "email123@email.com");

        String json = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/members/join")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is4xxClientError()
        ).andExpect(jsonPath("$.code").value("INCORRECT_DATA_FORMAT")
        ).andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 이메일 중복 미확인")
    void joinMemberFailTest2() throws Exception {
        // given
        String email = "email130@gmail.com";
        String password = "password123!";
        String nickname = "닉네임";
        JoinRequestDto requestDto = new JoinRequestDto(email, password, nickname);

        Cookie cookie = new Cookie("Email-Check", "delete");

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

    @Test
    @DisplayName("이메일 중복 확인")
    void duplicateCheckEmail() throws Exception {
        // given
        String email = "email123@gmail.com";
        EmailCheckRequestDto requestDto = new EmailCheckRequestDto(email);

        String json = objectMapper.writeValueAsString(requestDto);

        // when & then
        mvc.perform(post("/api/members/email/check")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(cookie().value("Email-Check", "email123@gmail.com")
        ).andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 -> 중복된 이메일")
    void duplicateCheckEmailFailTest1() throws Exception {

        // given
        String email = "email123@gmail.com";

        joinUseCase.join(email, "encodePassword", "nick");

        EmailCheckRequestDto requestDto = new EmailCheckRequestDto(email);
        String json = objectMapper.writeValueAsString(requestDto);

        // when & then
        mvc.perform(post("/api/members/email/check")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()
        ).andExpect(cookie().doesNotExist("Email-Check")
        ).andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 -> 올바르지 않은 이메일 형식")
    void duplicateCheckEmailFailTest2() throws Exception {
        // given
        String wrongEmail = "wrongEmail";
        EmailCheckRequestDto requestDto = new EmailCheckRequestDto(wrongEmail);
        String json = objectMapper.writeValueAsString(requestDto);

        // when & then
        mvc.perform(post("/api/members/email/check")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError()
        ).andExpect(cookie().doesNotExist("Email-Check")
        ).andExpect(jsonPath("$.code").isString()
        ).andExpect(jsonPath("$.message").isString()
        ).andDo(print());
    }
}
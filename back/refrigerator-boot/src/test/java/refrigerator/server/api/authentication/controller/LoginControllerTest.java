package refrigerator.server.api.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import refrigerator.server.api.authentication.dto.LoginRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("일반 로그인 성공 테스트")
    void loginByEmailSuccessTest() throws Exception {
        String email = "mstest102@gmail.com";
        String password = "password123!";
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new LoginRequestDTO(email, password))))
                .andExpect(status().isCreated())
                .andExpect(cookie().exists("Refresh-Token"))
                .andExpect(cookie().maxAge("Refresh-Token", 3600 * 24 * 30));
    }

    @Test
    @DisplayName("일반 로그인 실패 테스트 -> 잘못된 정보일 경우")
    void loginByEmailFailTest() throws Exception {
        String email = "wrongemail@gmail.com";
        String password = "password123!";
        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new LoginRequestDTO(email, password))))
                .andExpect(status().is4xxClientError());
    }
}
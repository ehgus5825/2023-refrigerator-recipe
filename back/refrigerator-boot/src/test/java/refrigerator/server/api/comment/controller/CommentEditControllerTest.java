package refrigerator.server.api.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.server.api.comment.dto.InCommentEditRequestDto;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentEditControllerTest {

    @Autowired MockMvc mvc;
    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void edit() throws Exception {
        String json = objectMapper.writeValueAsString(new InCommentEditRequestDto("content"));
        mvc.perform(put("/api/comments/1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}
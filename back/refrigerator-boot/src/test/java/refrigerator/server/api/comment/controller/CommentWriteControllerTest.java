package refrigerator.server.api.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.server.api.comment.dto.request.CommentWriteRequestDto;
import refrigerator.server.security.common.TestTokenService;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentWriteControllerTest {


    @Autowired MockMvc mvc;

    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("댓글 작성 API 성공 테스트")
    void writeSuccessTest() throws Exception {

        long recipeId = 1L;
        String content = "content";
        CommentWriteRequestDto requestDto = new CommentWriteRequestDto(content);
        String json = objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/api/recipe/"+recipeId+"/comments/write")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}
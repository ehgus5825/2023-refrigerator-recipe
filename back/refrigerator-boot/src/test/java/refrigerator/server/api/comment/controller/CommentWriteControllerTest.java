package refrigerator.server.api.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.server.api.comment.dto.InCommentWriteRequestDto;
import refrigerator.server.config.ExcludeSecurityConfiguration;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import static org.mockito.ArgumentMatchers.any;
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
        InCommentWriteRequestDto requestDto = new InCommentWriteRequestDto(content);
        String json = objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/api/recipe/"+recipeId+"/comments/write")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}
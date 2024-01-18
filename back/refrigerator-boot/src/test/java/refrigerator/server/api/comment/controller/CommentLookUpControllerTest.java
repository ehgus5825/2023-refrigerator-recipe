package refrigerator.server.api.comment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.server.security.common.TestTokenService;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentLookUpControllerTest {

    @Autowired MockMvc mvc;
    @Autowired JsonWebTokenUseCase jsonWebTokenUseCase;
    @Autowired WriteCommentUseCase writeCommentUseCase;

    @BeforeEach
    void setUp() {
        writeCommentUseCase.write(1L, "mstest102@gmail.com", "test comment1");
        writeCommentUseCase.write(1L, "jktest103@gmail.com", "test comment2");
        writeCommentUseCase.write(1L, "dhtest101@gmail.com", "test comment3");
        writeCommentUseCase.write(1L, "mstest102@gmail.com", "test comment4");
        writeCommentUseCase.write(1L, "jktest103@gmail.com", "test comment5");
        writeCommentUseCase.write(1L, "dhtest101@gmail.com", "test comment6");
        writeCommentUseCase.write(1L, "mstest102@gmail.com", "test comment7");
        writeCommentUseCase.write(1L, "jktest103@gmail.com", "test comment8");
        writeCommentUseCase.write(1L, "dhtest101@gmail.com", "test comment9");
    }

    @Test
    void findComments() throws Exception {
        mvc.perform(get("/api/recipe/1/comments?page=0")
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void findCommentPreview() throws Exception {
        mvc.perform(get("/api/recipe/1/comments/preview")
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}
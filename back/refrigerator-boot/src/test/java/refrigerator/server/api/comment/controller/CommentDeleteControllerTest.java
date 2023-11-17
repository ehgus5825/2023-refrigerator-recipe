package refrigerator.server.api.comment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentDeleteControllerTest {

    @Autowired MockMvc mvc;

    @Test
    @DisplayName("댓글 삭제 API 성공 테스트")
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/comments/1/delete")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}
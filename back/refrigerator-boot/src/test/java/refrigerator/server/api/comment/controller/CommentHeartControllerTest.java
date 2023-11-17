package refrigerator.server.api.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentHeartControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    JsonWebTokenUseCase jsonWebTokenUseCase;

    @Test
    void addHeartCount() {
    }

    @Test
    void reduceHeartCount() {
    }
}
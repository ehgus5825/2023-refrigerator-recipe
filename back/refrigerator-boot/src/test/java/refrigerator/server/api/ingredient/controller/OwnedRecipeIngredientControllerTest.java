package refrigerator.server.api.ingredient.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OwnedRecipeIngredientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonWebTokenUseCase jsonWebTokenUseCase;

//    @Test
    @DisplayName("레시피 식재료 중 소유하고 있는 식재료 조회")
    void getOwnedRecipeIngredientsTest() throws Exception {

        mockMvc.perform(get("/api/ingredients/owned/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("레시피 식재료 중 소유하고 있는 식재료 조회 : 알 수 없는 id")
    void getOwnedRecipeIngredientsTestFailUnknownId() throws Exception {

        mockMvc.perform(
                get("/api/ingredients/owned/684684138168")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}
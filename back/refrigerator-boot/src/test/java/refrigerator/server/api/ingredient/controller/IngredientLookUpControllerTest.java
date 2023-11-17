package refrigerator.server.api.ingredient.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.SaveRegisteredIngredientPort;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientLookUpControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired
    SaveIngredientPort saveIngredientPort;

    @Autowired
    JsonWebTokenUseCase jsonWebTokenUseCase;

    @Autowired
    SaveRegisteredIngredientPort saveRegisteredIngredientPort;

    @BeforeEach
    void setUp() {
        RegisteredIngredient.RegisteredIngredientBuilder builder = RegisteredIngredient.builder()
                .image(1)
                .unit("g");

        saveRegisteredIngredientPort.saveRegisteredIngredient(builder.name("콩나물").build());
        saveRegisteredIngredientPort.saveRegisteredIngredient(builder.name("안심").build());
        saveRegisteredIngredientPort.saveRegisteredIngredient(builder.name("감자").build());
    }

//    @Test
    @DisplayName("식재료 이름에 따른 용량단위 반환")
    void findIngredientUnitTest() throws Exception {

        String name = "감자";

        mockMvc.perform(
                get("/api/ingredients/unit?name=" + name)
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 이름에 따른 용량단위 반환 실패 : 등록되지 않은 식재료")
    void findIngredientUnitTestFailUnregisteredIngredients() throws Exception {

        String name = "파워에이드";

        mockMvc.perform(
                get("/api/ingredients/unit?name=" + name)
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 목록 조회")
    void findIngredientListTest() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("mstest102@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        saveIngredientPort.saveIngredient(ingredient);

        String storage = "냉장";
        String deadline = "true";

        mockMvc.perform(
                get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 목록 조회 실패 : 존재하지 않는 보관타입")
    void findIngredientListTestFailNotExistStorageType() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("mstest102@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        saveIngredientPort.saveIngredient(ingredient);

        String storage = "방관";
        String deadline = "false";

        mockMvc.perform(
                get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 목록 조회 실패 : Boolean 타입 오류")
    void findIngredientListTestFailBooleanTypeError() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("mstest102@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        saveIngredientPort.saveIngredient(ingredient);

        String storage = "냉장";
        String deadline = "test";

        mockMvc.perform(
                get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 검색")
    void searchIngredientListTest() throws Exception {

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .capacity(60.0)
                .email("mstest102@gmail.com")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g");

        saveIngredientPort.saveIngredient(builder.name("콩나물").storageMethod(IngredientStorageType.FRIDGE).build());
        saveIngredientPort.saveIngredient(builder.name("안심").storageMethod(IngredientStorageType.FREEZER).build());

        mockMvc.perform(
                get("/api/ingredients/search")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 단건 조회")
    void findIngredientTest() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("콩나물")
                .capacity(60.0)
                .email("mstest102@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        mockMvc.perform(
                get("/api/ingredients/" + id)
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 단건 조회_실패 : 알 수 없는 id")
    void findIngredientTestFail() throws Exception {

        mockMvc.perform(
                get("/api/ingredients/51651656165")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("임박 식재료 목록 조회")
    void findIngredientListByDeadlineTest() throws Exception {

        LocalDate expirationDate = LocalDate.now().plusDays(1);

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("mstest102@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(expirationDate)
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        saveIngredientPort.saveIngredient(ingredient);

        String days = "1";

        mockMvc.perform(
                get("/api/ingredients/deadline/" + days)
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}
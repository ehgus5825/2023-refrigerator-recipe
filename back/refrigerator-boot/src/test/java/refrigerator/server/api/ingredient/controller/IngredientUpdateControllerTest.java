package refrigerator.server.api.ingredient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.SaveRegisteredIngredientPort;
import refrigerator.server.api.global.common.BasicListRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientRegisterRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientUpdateRequestDTO;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientUpdateControllerTest {

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
    @DisplayName("식재료 등록")
    void registerIngredientTest() throws Exception {

        // 식재료 사전 등록
        
        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(30.0)
                .storage(IngredientStorageType.ROOM)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 등록 실패 : NULL")
    void registerIngredientTestFailNullValue() throws Exception {

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name(null)
                .expirationDate(null)
                .volume(null)
                .storage(null)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 등록 실패 : DTO 값 누락")
    void registerIngredientTestFailOmissionDTOValue() throws Exception {

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .storage(IngredientStorageType.FREEZER)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 등록 실패 : 등록되지 않은 식재료")
    void registerIngredientTestFailUnregisteredIngredients() throws Exception {

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("파워에이드")
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(30.0)
                .storage(IngredientStorageType.ROOM)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 등록 실패 : 용량 음수값")
    void registerIngredientTestFailMinusVolume() throws Exception {
        
        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(-1.0)
                .storage(IngredientStorageType.ROOM)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

//    @Test
    @DisplayName("식재료 등록 실패 : 용량 범위 초과")
    void registerIngredientTestFailOverVolume() throws Exception {

        // 식재료 사전 등록
        
        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(10000.0)
                .storage(IngredientStorageType.ROOM)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 등록 실패 : 존재하지 않는 보관타입")
    void registerIngredientTestFailNotExistStorageType() throws Exception {

        String content = "{\"name\":\"감자\",\"expirationDate\":\"2023-05-23\",\"volume\":30.0,\"storage\":\"방관\"}";

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 등록 실패 : 지정되지 않은 날짜형식")
    void registerIngredientTestFailInvalidDateFormat() throws Exception {

        String content = "{\"name\":\"감자\",\"expirationDate\":\"2023/05/23\",\"volume\":30.0,\"storage\":\"냉장\"}";

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 수정")
    void modifyIngredientTest() throws Exception {

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(30.0)
                .storage(IngredientStorageType.FREEZER)
                .build();

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 수정 실패 : NULL")
    void modifyIngredientTestFailNullValue() throws Exception {

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(null)
                .volume(null)
                .storage(null)
                .build();

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 수정 실패 : DTO 값 누락")
    void modifyIngredientTestFailOmissionDTOValue() throws Exception {

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.of(2023,1,1))
                .build();

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 수정 실패 : 용량 음수값")
    void modifyIngredientTestFailMinusVolume() throws Exception {

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(-1.0)
                .storage(IngredientStorageType.FREEZER)
                .build();

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 수정 실패 : 용량범위 초과")
    void modifyIngredientTestFailOverVolume() throws Exception {

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.of(2023,1,1))
                .volume(10000.0)
                .storage(IngredientStorageType.FREEZER)
                .build();

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
    @Test
    @DisplayName("식재료 수정 실패 : 존재하지 않는 보관타입")
    void modifyIngredientTestFailNotExistStorageType() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = "{\"expirationDate\":\"2023-05-23\",\"volume\":30.0,\"storage\":\"방관\"}";

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 수정 실패 : 지정되지 않은 날짜형식")
    void modifyIngredientTestFailInvalidDateFormat() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        String content = "{\"expirationDate\":\"2023/05/23\",\"volume\":30.0,\"storage\":\"냉동\"}";

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 삭제")
    void removeIngredientTest() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = saveIngredientPort.saveIngredient(ingredient);

        mockMvc.perform(
                delete("/api/ingredients/" + id)
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 일괄 삭제")
    void removeAllIngredientTest() throws Exception {

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g");

        List<Long> ids = new ArrayList<>();
        ids.add(saveIngredientPort.saveIngredient(builder.name("안심").build()));
        ids.add(saveIngredientPort.saveIngredient(builder.name("감자").build()));
        ids.add(saveIngredientPort.saveIngredient(builder.name("호박").build()));

        BasicListRequestDTO<Long> request = BasicListRequestDTO.<Long>builder()
                .data(ids)
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 일괄 삭제 실패 : NULL")
    void removeAllIngredientTestFailNullValue() throws Exception {

        BasicListRequestDTO<Long> request = BasicListRequestDTO.<Long>builder()
                .data(null)
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 일괄 삭제 실패 : 값 누락")
    void removeAllIngredientTestFailOmissionValue() throws Exception {

        BasicListRequestDTO<Long> request = BasicListRequestDTO.<Long>builder()
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 일괄 삭제 실패 : 빈 리스트")
    void removeAllIngredientTestFailEmptyList() throws Exception {

        BasicListRequestDTO<Long> request = BasicListRequestDTO.<Long>builder()
                .data(new ArrayList<>())
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}
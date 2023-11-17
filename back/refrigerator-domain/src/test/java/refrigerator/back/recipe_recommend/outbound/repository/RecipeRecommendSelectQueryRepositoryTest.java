package refrigerator.back.recipe_recommend.outbound.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.recipe_recommend.outbound.dto.OutMyIngredientDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecipeIngredientDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecommendRecipeDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, RecipeRecommendSelectQueryRepository.class})
@TestDataInit({"/ingredient.sql", "recipe_ingredient.sql", "/recipe.sql"})
@Slf4j
class RecipeRecommendSelectQueryRepositoryTest {

    @Autowired RecipeRecommendSelectQueryRepository queryRepository;

    @Test
    void selectRecipeIngredientDtoList() {
        List<OutRecipeIngredientDto> result = queryRepository.selectRecipeIngredientDtoList();
        for (OutRecipeIngredientDto dto : result) {
            log.info("dto={}", dto);
        }
        assertEquals(8, result.size());
    }

    @Test
    void selectMyIngredientNamesTest1() {
        String memberId = "jktest101@gmail.com";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        List<OutMyIngredientDto> result = queryRepository.selectMyIngredientNames(startDate, memberId);
        for (OutMyIngredientDto dto : result) {
            log.info("dto={}", dto);
        }
        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("지정한 날짜 사이의 식재료를 제대로 가져오는지 확인")
    void selectMyIngredientNamesTest2() {
        String memberId = "jktest101@gmail.com";
        LocalDate startDate = LocalDate.of(2023, 12, 26);
        List<OutMyIngredientDto> result = queryRepository.selectMyIngredientNames(startDate, memberId);
        for (OutMyIngredientDto dto : result) {
            log.info("dto={}", dto);
        }
        assertEquals(2, result.size());
    }

    @Test
    void selectRecipeInfoByIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<OutRecommendRecipeDto> result = queryRepository.selectRecipeInfoByIds(ids);
        for (OutRecommendRecipeDto dto : result) {
            log.info("dto={}", dto);
        }
    }
}
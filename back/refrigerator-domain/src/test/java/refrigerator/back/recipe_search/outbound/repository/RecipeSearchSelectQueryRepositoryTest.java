package refrigerator.back.recipe_search.outbound.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.recipe.application.domain.entity.RecipeCategory;
import refrigerator.back.recipe.application.domain.entity.RecipeFoodType;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.outbound.dto.OutRecipeSearchDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, RecipeSearchSelectQueryRepository.class})
@TestDataInit("/recipe.sql")
class RecipeSearchSelectQueryRepositoryTest {

    @Autowired RecipeSearchSelectQueryRepository queryRepository;

    @Test
    @DisplayName("레시피 검색 테스트 -> 조건이 없을 때")
    void selectSearchRecipeDtosTest1() {
        RecipeSearchCondition condition = RecipeSearchCondition.builder().build();
        List<OutRecipeSearchDto> result = queryRepository.selectSearchRecipeDtos(condition, PageRequest.of(0, 11));
        System.out.println("result = " + result);
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("레시피 검색 테스트 -> 조건이 있을 때")
    void selectSearchRecipeDtosTest2() {
        RecipeSearchCondition condition = RecipeSearchCondition.builder()
                .searchWord("나물")
                .build();
        List<OutRecipeSearchDto> result = queryRepository.selectSearchRecipeDtos(condition, PageRequest.of(0, 11));
        System.out.println("result = " + result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("레시피 타입 조회")
    void selectRecipeFoodTypes(){
        List<RecipeFoodType> result = queryRepository.selectRecipeFoodTypes();
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("레시피 카테고리 조회")
    void selectRecipeCategories(){
        List<RecipeCategory> result = queryRepository.selectRecipeCategories();
        System.out.println("result = " + result);
    }
}
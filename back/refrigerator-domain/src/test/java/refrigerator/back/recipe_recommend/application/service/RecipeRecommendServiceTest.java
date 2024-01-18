package refrigerator.back.recipe_recommend.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe_recommend.RecipeRecommendConfig;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;
import refrigerator.back.recipe_recommend.application.port.in.RecommendRecipeUseCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class RecipeRecommendServiceTest {

    @Autowired RecommendRecipeUseCase recommendRecipeUseCase;

    @Test
    @DisplayName("레시피 추천 테스트")
    void recommend() {
        String memberId = "mstest102@gmail.com";

        List<RecommendRecipeDto> result = recommendRecipeUseCase.recommend(memberId);
        result.forEach(item -> {
            int percent = Integer.parseInt(item.getPercent());
            assertTrue(percent > 0 && percent <= 100);
        });
    }
}
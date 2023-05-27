package refrigerator.back.recipe.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class RecipeRecommendServiceTest {

    @Autowired RecipeRecommendService recipeRecommendService;

    @Test
    @DisplayName("레시피 추천")
    void recommend() {
        String memberId = "mstest102@gmail.com";
        List<InRecipeRecommendDTO> result = recipeRecommendService.recommend(memberId);
        result.forEach(item -> assertThat(item.getMatch()>=0.0 && item.getMatch() <=100.0).isTrue());
    }

}
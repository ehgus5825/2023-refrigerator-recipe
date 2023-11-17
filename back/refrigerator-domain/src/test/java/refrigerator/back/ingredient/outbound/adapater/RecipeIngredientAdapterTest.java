package refrigerator.back.ingredient.outbound.adapater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.ingredient.outbound.mapper.OutRecipeIngredientMapperConfig;
import refrigerator.back.ingredient.outbound.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Import({QuerydslConfig.class, SubIngredientQueryRepository.class,
        RecipeIngredientAdapter.class, OutRecipeIngredientMapperConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/ingredient.sql"})
class RecipeIngredientAdapterTest {

    @Autowired RecipeIngredientAdapter recipeIngredientAdapter;

    @Test
    @DisplayName("레시피 id에 따른 식재료 리스트 조회")
    void getRecipeIngredient() {

        assertThat(recipeIngredientAdapter.getRecipeIngredient(1L).size())
                .isEqualTo(8);

        assertThat(recipeIngredientAdapter.getRecipeIngredient(1L).get(0))
                .isInstanceOf(RecipeIngredientDto.class);
    }
}
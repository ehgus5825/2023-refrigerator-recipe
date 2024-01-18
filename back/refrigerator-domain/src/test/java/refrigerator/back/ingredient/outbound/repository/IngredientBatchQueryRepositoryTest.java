package refrigerator.back.ingredient.outbound.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;
import refrigerator.back.ingredient.outbound.repository.query.IngredientBatchQueryRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QuerydslConfig.class, IngredientBatchQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/ingredientImage.sql", "/ingredient.sql"})
@Slf4j
class IngredientBatchQueryRepositoryTest {

    @Autowired
    IngredientBatchQueryRepository ingredientBatchQueryRepository;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("요청 식재료 데이터 모두 삭제")
    void deleteSuggestedIngredientTest() {
        String name = "감자";

        SuggestedIngredient ingredient = SuggestedIngredient.builder()
                .name(name)
                .email("email123@gmail.com")
                .unit("g")
                .build();

        Long id = em.persistAndGetId(ingredient, Long.class);

        ingredientBatchQueryRepository.deleteSuggestedIngredient();

        assertThat(em.find(SuggestedIngredient.class, id))
                .isNull();
    }

    @Test
    @DisplayName("deleted 상태인 식재료 데이터 모두 삭제")
    void deleteIngredientsTest() {

        // given
        Ingredient ingredient = Ingredient.create(
                "감자",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 1),
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                "email123@gmail.com");

        ingredient.delete();

        Long id = em.persistAndGetId(ingredient, Long.class);

        // when
        ingredientBatchQueryRepository.deleteIngredients();

        // then
        assertThat(em.find(Ingredient.class, id)).isNull();
    }

}
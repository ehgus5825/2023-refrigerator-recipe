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
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;
import refrigerator.back.ingredient.outbound.repository.query.SubIngredientQueryRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QuerydslConfig.class, SubIngredientQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/ingredientImage.sql", "/ingredient.sql"})
@Slf4j
class SubIngredientQueryRepositoryTest {

    @Autowired SubIngredientQueryRepository subIngredientQueryRepository;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("등록된 식재료 목록 조회")
    void getRegisteredIngredientListTest() {
        List<RegisteredIngredient> list = subIngredientQueryRepository.getRegisteredIngredientList();

        assertThat(list.size()).isEqualTo(8);
    }

    @Test
    @DisplayName("요청된 식재료 저장 테스트")
    void saveSuggestIngredientTest() {
        SuggestedIngredient ingredient = SuggestedIngredient.builder()
                .name("감자")
                .email("email123@gmail.com")
                .unit("g")
                .build();

        Long id = subIngredientQueryRepository.saveSuggestIngredient(ingredient);

        SuggestedIngredient suggestedIngredient = em.find(SuggestedIngredient.class, id);

        assertThat(suggestedIngredient.getId()).isEqualTo(id);
        assertThat(suggestedIngredient.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(suggestedIngredient.getName()).isEqualTo("감자");
        assertThat(suggestedIngredient.getUnit()).isEqualTo("g");
    }

    @Test
    @DisplayName("레시피 ID에 맞는 레시피 식재료 목록 조회")
    void getRecipeIngredientTest() {

        List<OutIngredientInRecipeDTO> list = subIngredientQueryRepository.getRecipeIngredient(1L);

        assertThat(list.size()).isEqualTo(8);
    }

    @Test
    void findUnitNameTest(){

        String name = "감자";

        SuggestedIngredient.SuggestedIngredientBuilder builder = SuggestedIngredient.builder()
                .name(name)
                .email("email123@gmail.com");

        em.persist(builder.unit("g").build());
        em.persist(builder.unit("g").build());
        em.persist(builder.unit("g").build());
        em.persist(builder.unit("g").build());
        em.persist(builder.unit("g").build());
        em.persist(builder.unit("개").build());
        em.persist(builder.unit("개").build());
        em.persist(builder.unit("개").build());
        em.persist(builder.unit("개").build());
        em.persist(builder.unit("ml").build());
        em.persist(builder.unit("ml").build());
        em.persist(builder.unit("ml").build());
        em.persist(builder.unit("장").build());
        em.persist(builder.unit("장").build());
        em.persist(builder.unit("대").build());

        subIngredientQueryRepository.findUnitName(name).ifPresent(
                unitName -> {
                    log.info("enter");
                    assertThat(unitName).isEqualTo("g");
                }
        );
    }
}
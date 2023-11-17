package refrigerator.back.ingredient.outbound.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class IngredientPersistenceRepositoryTest {

    @Autowired IngredientPersistenceRepository ingredientPersistenceRepository;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("식재료 추가 테스트")
    void addIngredientTest() {

        // given
        String email = "email123@gmail.com";
        LocalDate now = LocalDate.of(2023, 1, 1);

        Ingredient ingredient = Ingredient.create(
                "감자",
                now,
                now,
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                email);

        // when
        Ingredient saveIngredient = ingredientPersistenceRepository.save(ingredient);

        // then
        assertThat(saveIngredient.getName()).isEqualTo("감자");
        assertThat(saveIngredient.getRegistrationDate()).isEqualTo(now);
        assertThat(saveIngredient.getExpirationDate()).isEqualTo(now);
        assertThat(saveIngredient.getCapacity()).isEqualTo(30.0);
        assertThat(saveIngredient.getCapacityUnit()).isEqualTo("g");
        assertThat(saveIngredient.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(saveIngredient.getImage()).isEqualTo(1);
        assertThat(saveIngredient.getEmail()).isEqualTo(email);
        assertThat(saveIngredient.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("이메일이 일치하고 삭제 상태가 false인 식재료 리스트 조회")
    void findByEmailAndDeletedTrueTest() {

        //given
        String email = "email123@gmail.com";
        LocalDate now = LocalDate.of(2023, 1, 1);

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .expirationDate(now)
                .registrationDate(now)
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        // 조회 O
        Long id = em.persistAndGetId(builder.name("감자").email(email).deleted(false).build(), Long.class);
        // 조회 X : 삭제 상태 true
        em.persist(builder.name("고구마").email(email).deleted(true).build());
        // 조회 X : email 불일치
        em.persist(builder.name("양파").email("email456@gmail.com").deleted(false).build());
        
        // when
        List<Ingredient> ingredients = ingredientPersistenceRepository.findByEmailAndDeletedFalse(email);

        // then
        assertThat(ingredients.size()).isEqualTo(1);

        Ingredient ingredient = ingredients.get(0);
        assertThat(ingredient.getId()).isEqualTo(id);
        assertThat(ingredient.getName()).isEqualTo("감자");
        assertThat(ingredient.getExpirationDate()).isEqualTo(now);
        assertThat(ingredient.getRegistrationDate()).isEqualTo(now);
        assertThat(ingredient.getCapacity()).isEqualTo(30.0);
        assertThat(ingredient.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(ingredient.getCapacityUnit()).isEqualTo("g");
        assertThat(ingredient.getImage()).isEqualTo(1);
        assertThat(ingredient.getEmail()).isEqualTo(email);
        assertThat(ingredient.isDeleted()).isEqualTo(false);
    }

    @Test
    @DisplayName("id가 일치하고 삭제 상태가 false인 식재료 리스트 조회")
    void findByIdAndDeletedFalseTest() {

        // given
        String email = "email123@gmail.com";
        LocalDate now = LocalDate.of(2023, 1, 1);

        Ingredient ingredient = Ingredient.create(
                "감자",
                now,
                now,
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                "email123@gmail.com"
                );

        // when, then

        // case 1 : 정상
        final Long id = em.persistAndGetId(ingredient, Long.class);

        ingredientPersistenceRepository.findByIdAndDeletedFalse(id).ifPresent(
                ingredient1 -> {
                    log.info("enter");
                    assertThat(ingredient.getId()).isEqualTo(id);
                    assertThat(ingredient.getName()).isEqualTo("감자");
                    assertThat(ingredient.getExpirationDate()).isEqualTo(now);
                    assertThat(ingredient.getRegistrationDate()).isEqualTo(now);
                    assertThat(ingredient.getCapacity()).isEqualTo(30.0);
                    assertThat(ingredient.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
                    assertThat(ingredient.getCapacityUnit()).isEqualTo("g");
                    assertThat(ingredient.getImage()).isEqualTo(1);
                    assertThat(ingredient.getEmail()).isEqualTo(email);
                    assertThat(ingredient.isDeleted()).isEqualTo(false);
                }
        );

        // case 2 : id 불일치

        assertThat(ingredientPersistenceRepository.findByIdAndDeletedFalse(-1L).orElse(null))
                .isNull();
        
        // case 3 : 삭제 상태 true
        
        ingredient.delete();

        em.flush();

        assertThat(ingredientPersistenceRepository.findByIdAndDeletedFalse(id).orElse(null))
                .isNull();
    }
}
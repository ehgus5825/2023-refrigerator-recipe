package refrigerator.back.ingredient.outbound.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.outbound.repository.query.IngredientUpdateQueryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({QuerydslConfig.class, IngredientUpdateQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IngredientUpdateQueryRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    IngredientUpdateQueryRepository ingredientUpdateQueryRepository;

    @Test
    @DisplayName("식재료 삭제 상태 변경 테스트")
    void updateIngredientDeleteStateTrueTest() {

        // given
        Ingredient ingredient = Ingredient.builder()
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();

        Long id = em.persistAndGetId(ingredient, Long.class);

        assertThat(em.find(Ingredient.class, id).isDeleted())
                .isFalse();

        // when
        ingredientUpdateQueryRepository.updateIngredientDeleteStateTrue(id);

        // then
        assertThat(em.find(Ingredient.class, id).isDeleted())
                .isTrue();
    }

    @Test
    @DisplayName("식재료 삭제 상태 일괄 변경 테스트")
    void updateAllIngredientDeleteStateTrueTest() {

        // given
        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        List<Long> ids = new ArrayList<>();

        ids.add(em.persistAndGetId(builder.name("감자").build(), Long.class));
        ids.add(em.persistAndGetId(builder.name("고구마").build(), Long.class));

        assertThat(em.find(Ingredient.class, ids.get(0)).isDeleted()).isFalse();
        assertThat(em.find(Ingredient.class, ids.get(1)).isDeleted()).isFalse();

        // when
        ingredientUpdateQueryRepository.updateAllIngredientDeleteStateTrue(ids);

        // then
        assertThat(em.find(Ingredient.class, ids.get(0)).isDeleted()).isTrue();
        assertThat(em.find(Ingredient.class, ids.get(1)).isDeleted()).isTrue();
    }

    @Test
    @DisplayName("식재료 용량 수정")
    void updateVolumeIngredient() {

        Ingredient ingredient = Ingredient.builder()
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();

        Long id = em.persistAndGetId(ingredient, Long.class);

        ingredientUpdateQueryRepository.updateVolumeIngredient(id, 40.0);

        assertThat(em.find(Ingredient.class, id).getCapacity()).isEqualTo(40.0);
    }

    @Test
    @DisplayName("idCheck 테스트")
    void idCheck() {
        assertThatThrownBy(() -> ingredientUpdateQueryRepository.idCheck(null))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("idListCheck 테스트")
    void idListCheck() {
        assertThatThrownBy(() -> ingredientUpdateQueryRepository.idListCheck(null))
                .isInstanceOf(BusinessException.class);

        ArrayList<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(null);

        assertThatThrownBy(() -> ingredientUpdateQueryRepository.idListCheck(list))
                .isInstanceOf(BusinessException.class);
    }
}

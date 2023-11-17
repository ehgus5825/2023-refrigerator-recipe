package refrigerator.back.ingredient.outbound.adapater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.s3.S3TestConfiguration;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapperConfig;
import refrigerator.back.ingredient.outbound.repository.IngredientLookUpQueryRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = {S3TestConfiguration.class, })
@Import({QuerydslConfig.class, OutIngredientMapperConfig.class,
        IngredientLookUpQueryRepository.class, IngredientLookUpAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/ingredientImage.sql", "/ingredient.sql"})
class IngredientLookUpAdapterTest {

    @Autowired TestEntityManager em;

    @Autowired IngredientLookUpAdapter ingredientLookUpAdapter;

    @Autowired IngredientLookUpQueryRepository ingredientLookUpQueryRepository;

    @Test
    @DisplayName("id에 따른 식재료 엔티티 조회 테스트 => 예외 확인")
    void getIngredientTest() {
        assertThatThrownBy(() -> ingredientLookUpAdapter.getIngredient(-1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("id에 따른 식재료 단건 상세 조회 테스트 => 예외 확인, mapper 테스트")
    void getIngredientDetailTest() {

        assertThatThrownBy(() -> ingredientLookUpAdapter
                .getIngredientDetail(-1L))
                .isInstanceOf(BusinessException.class);

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

        assertThat(ingredientLookUpAdapter.getIngredientDetail(id))
                .isInstanceOf(IngredientDetailDTO.class);
    }

    @Test
    @DisplayName("mapper 테스트")
    void mapperTest() {

        LocalDate now = LocalDate.of(2023,1,1);

        Ingredient ingredient = Ingredient.builder()
                .name("감자")
                .expirationDate(now)
                .registrationDate(now)
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();

        em.persist(ingredient);

        assertThat(ingredientLookUpAdapter.mapper(ingredientLookUpQueryRepository
                .findIngredientListOfAll("email123@gmail.com")).get(0))
                .isInstanceOf(IngredientDTO.class);
    }
}
package refrigerator.back.ingredient.outbound.adapater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.ingredient.IngredientConfig;
import refrigerator.back.ingredient.outbound.repository.query.IngredientLookUpQueryRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({QuerydslConfig.class, IngredientSubLookUpAdapter.class,
        IngredientLookUpQueryRepository.class, IngredientConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/ingredientImage.sql", "/ingredient.sql"})
public class IngredientSubLookUpAdapterTest {

    @Autowired IngredientSubLookUpAdapter ingredientSubLookUpAdapter;

    @Test
    @DisplayName("id에 따른 식재료 엔티티 조회 테스트 => 예외 확인")
    void getIngredientTest() {
        assertThatThrownBy(() -> ingredientSubLookUpAdapter.getIngredient(-1L))
                .isInstanceOf(BusinessException.class);
    }
}

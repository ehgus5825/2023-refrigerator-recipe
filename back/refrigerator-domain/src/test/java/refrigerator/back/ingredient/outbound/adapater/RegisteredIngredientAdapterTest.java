package refrigerator.back.ingredient.outbound.adapater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.outbound.repository.SubIngredientQueryRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, RegisteredIngredientAdapter.class,
        SubIngredientQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/ingredientImage.sql", "/ingredient.sql"})
class RegisteredIngredientAdapterTest {

    @Autowired RegisteredIngredientAdapter registeredIngredientAdapter;

    @Test
    @DisplayName("식재료명에 따른 등록된 식재료 단건 조회")
    void findIngredient() {

        assertThat(registeredIngredientAdapter.findIngredient("감자").getName())
                .isEqualTo("감자");

        assertThatThrownBy(() -> registeredIngredientAdapter.findIngredient("생선"))
                .isInstanceOf(BusinessException.class);
    }
}
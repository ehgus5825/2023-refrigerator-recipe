package refrigerator.back.ingredient.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.FindRegisteredIngredientPort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RegisteredIngredientServiceTest {

    @InjectMocks RegisteredIngredientService registeredIngredientService;

    @Mock FindRegisteredIngredientPort findRegisteredIngredientPort;

    @Test
    @DisplayName("식재료 이름에 따른 등록된 식재료 반환 테스트")
    void getIngredientTest() {

        RegisteredIngredient ingredient = RegisteredIngredient.builder()
                .id(1L)
                .name("당근")
                .image(1)
                .unit("g")
                .build();

        given(findRegisteredIngredientPort.findIngredient("당근"))
                .willReturn(ingredient);

        assertThat(registeredIngredientService.getIngredient("당근"))
                .isEqualTo(ingredient);
    }

    @Test
    @DisplayName("등록된 식재료 전체 반환 테스트")
    void getIngredientListTest() {

        RegisteredIngredient.RegisteredIngredientBuilder builder = RegisteredIngredient.builder()
                .image(1)
                .unit("g");

        List<RegisteredIngredient> ingredients = new ArrayList<>();
        ingredients.add(builder.id(1L).name("당근").build());
        ingredients.add(builder.id(2L).name("감자").build());
        ingredients.add(builder.id(3L).name("호박").build());
        ingredients.add(builder.id(4L).name("고구마").build());

        given(findRegisteredIngredientPort.findIngredientList())
                .willReturn(ingredients);

        assertThat(registeredIngredientService.getIngredientList())
                .isEqualTo(ingredients);
    }
}
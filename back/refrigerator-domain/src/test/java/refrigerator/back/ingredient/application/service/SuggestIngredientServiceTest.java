package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.ingredient.application.port.out.suggestedIngredient.SaveSuggestedIngredientPort;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SuggestIngredientServiceTest {

    @InjectMocks
    SuggestIngredientService suggestIngredientService;

    @Mock
    SaveSuggestedIngredientPort saveSuggestedIngredientPort;

    @Test
    @DisplayName("식재료 요청")
    void proposeIngredientTest() {

        given(saveSuggestedIngredientPort.proposeIngredient(any()))
                .willReturn(1L);

        assertThat(suggestIngredientService.proposeIngredient("감자", "g", "email123@gmail.com"))
                .isEqualTo(1L);
    }

}
package refrigerator.back.ingredient.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OutRecipeIngredientMapperTest {

    OutRecipeIngredientMapper outIngredientMapper = Mappers.getMapper(OutRecipeIngredientMapper.class);

    @Test
    @DisplayName("OutIngredientInRecipeDTO에서 OutIngredientInRecipeDTO로 변환")
    void toIngredientDetailDtoTest() {

        OutIngredientInRecipeDTO outDto = new OutIngredientInRecipeDTO(
                1L,
                "감자");

        RecipeIngredientDto dto = outIngredientMapper.toRecipeIngredientDto(outDto);

        assertThat(dto.getIngredientId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
    }
}
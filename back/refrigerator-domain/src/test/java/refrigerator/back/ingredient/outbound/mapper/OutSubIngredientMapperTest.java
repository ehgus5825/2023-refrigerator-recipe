package refrigerator.back.ingredient.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;
import refrigerator.back.ingredient.outbound.dto.OutMyIngredientDTO;

import static org.assertj.core.api.Assertions.assertThat;

class OutSubIngredientMapperTest {

    OutSubIngredientMapper outIngredientMapper = Mappers.getMapper(OutSubIngredientMapper.class);

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

    @Test
    @DisplayName("OutMyIngredientDTO에서 MyIngredientDTO로 변환")
    void toMyIngredientDTOTest(){

        OutMyIngredientDTO outDto = new OutMyIngredientDTO(
                1L,
                "감자",
                "g",
                30.0
        );

        MyIngredientDTO dto = outIngredientMapper.toMyIngredientDTO(outDto);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getVolume()).isEqualTo(30.0);
    }
}
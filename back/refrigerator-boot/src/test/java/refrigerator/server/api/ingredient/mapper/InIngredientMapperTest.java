package refrigerator.server.api.ingredient.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.IngredientUnitDTO;
import refrigerator.server.api.ingredient.dto.IngredientDeductionRequestDTO;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class InIngredientMapperTest {

    InIngredientMapper inIngredientMapper = Mappers.getMapper(InIngredientMapper.class);

    @Test
    @DisplayName("IngredientSearchCondition로 변환")
    void toIngredientSearchConditionTest(){

        IngredientSearchCondition ingredientSearchCondition = inIngredientMapper.toIngredientSearchCondition(IngredientStorageType.FRIDGE, false, "email123@gmail.com");

        assertThat(ingredientSearchCondition.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(ingredientSearchCondition.getDeadline()).isEqualTo(false);
        assertThat(ingredientSearchCondition.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
    }

    @Test
    @DisplayName("IngredientDeductionRequestDTO에서 IngredientDeductionDTO로 변환")
    void toIngredientDeductionDTOTest() {

        IngredientDeductionRequestDTO requestDTO = IngredientDeductionRequestDTO.builder()
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build();

        IngredientDeductionDTO dto = inIngredientMapper.toIngredientDeductionDTO(requestDTO);

        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getVolume()).isEqualTo(30.0);
    }

    @Test
    @DisplayName("RegisteredIngredient에서 IngredientUnitDTO로 변환")
    void toIngredientUnitResponseDTOTest() {

        RegisteredIngredient ingredient = RegisteredIngredient.builder()
                .id(1L)
                .name("감자")
                .unit("g")
                .image(1)
                .build();

        IngredientUnitDTO dto = inIngredientMapper.toIngredientUnitResponseDTO(ingredient);

        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getName()).isEqualTo("감자");
    }

}
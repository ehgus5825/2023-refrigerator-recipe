package refrigerator.back.ingredient.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.outbound.dto.OutMyIngredientDTO;


@Mapper(componentModel = "spring")
public interface OutSubIngredientMapper {

    OutSubIngredientMapper INSTANCE = Mappers.getMapper(OutSubIngredientMapper.class);

    RecipeIngredientDto toRecipeIngredientDto(OutIngredientInRecipeDTO dto);

    MyIngredientDTO toMyIngredientDTO(OutMyIngredientDTO outMyIngredientDTO);
}

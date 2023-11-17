package refrigerator.back.ingredient.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;


@Mapper(componentModel = "spring")
public interface OutRecipeIngredientMapper {

    OutRecipeIngredientMapper INSTANCE = Mappers.getMapper(OutRecipeIngredientMapper.class);

    RecipeIngredientDto toRecipeIngredientDto(OutIngredientInRecipeDTO dto);
}

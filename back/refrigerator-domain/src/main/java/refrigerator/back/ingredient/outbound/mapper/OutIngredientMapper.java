package refrigerator.back.ingredient.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDTO;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;


@Mapper(componentModel = "spring")
public interface OutIngredientMapper {

    OutIngredientMapper INSTANCE = Mappers.getMapper(OutIngredientMapper.class);

    IngredientDetailDTO toIngredientDetailDto(OutIngredientDetailDTO outIngredientDetailDTO, String image);

    IngredientDTO toIngredientDto(OutIngredientDTO outIngredientDTO, String image);
}

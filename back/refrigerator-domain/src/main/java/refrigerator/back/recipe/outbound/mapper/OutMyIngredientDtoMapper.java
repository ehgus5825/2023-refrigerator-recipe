package refrigerator.back.recipe.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.dto.MyIngredientDto;
import refrigerator.back.recipe.outbound.dto.OutMyIngredientDto;

@Mapper(componentModel = "spring")
public interface OutMyIngredientDtoMapper {

    OutMyIngredientDtoMapper INSTANCE = Mappers.getMapper(OutMyIngredientDtoMapper.class);

    MyIngredientDto toMyIngredientDto(OutMyIngredientDto dto);

}

package refrigerator.back.recipe_search.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.back.recipe_search.outbound.dto.OutRecipeSearchDto;

@Mapper(componentModel = "spring")
public interface OutRecipeSearchDtoMapper {

    OutRecipeSearchDtoMapper INSTANCE = Mappers.getMapper(OutRecipeSearchDtoMapper.class);

    @Mapping(target = "recipeImage", source = "dto.recipeImageName")
    RecipeSearchDto toInRecipeSearchDto(OutRecipeSearchDto dto);
}

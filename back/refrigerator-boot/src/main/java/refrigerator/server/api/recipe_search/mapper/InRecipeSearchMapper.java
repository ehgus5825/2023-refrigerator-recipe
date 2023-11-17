package refrigerator.server.api.recipe_search.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.server.api.recipe_search.dto.InRecipeSearchConditionDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;

@Mapper(componentModel = "spring")
public interface InRecipeSearchMapper {

    InRecipeSearchMapper INSTANCE = Mappers.getMapper(InRecipeSearchMapper.class);

    RecipeSearchCondition toRecipeSearchCondition(InRecipeSearchConditionDto dto, String searchWord);
}

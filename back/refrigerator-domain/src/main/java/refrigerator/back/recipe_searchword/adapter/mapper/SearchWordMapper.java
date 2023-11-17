package refrigerator.back.recipe_searchword.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_searchword.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.recipe_searchword.application.domain.Ingredient;

@Mapper(componentModel = "spring")
public interface SearchWordMapper {

    SearchWordMapper INSTANCE = Mappers.getMapper(SearchWordMapper.class);

    Ingredient toIngredient(OutIngredientDTO dto);
}

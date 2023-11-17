package refrigerator.server.api.recipe_search.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.server.api.recipe_search.dto.InRecipeSearchConditionDto;

import static org.junit.jupiter.api.Assertions.*;

class InRecipeSearchMapperTest {

    InRecipeSearchMapper mapper = Mappers.getMapper(InRecipeSearchMapper.class);

    @Test
    @DisplayName("모든 필드가 null 인 값이 들어왔을 때")
    void toRecipeSearchCondition() {
        String searchWord = "감자";
        InRecipeSearchConditionDto request = InRecipeSearchConditionDto.builder().build();
        RecipeSearchCondition result = mapper.toRecipeSearchCondition(request, searchWord);
        assertEquals(result, RecipeSearchCondition.builder().searchWord(searchWord).build());
    }
}
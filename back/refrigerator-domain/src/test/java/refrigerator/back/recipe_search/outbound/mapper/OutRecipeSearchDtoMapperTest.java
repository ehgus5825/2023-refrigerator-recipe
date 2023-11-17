package refrigerator.back.recipe_search.outbound.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.back.recipe_search.outbound.dto.OutRecipeSearchDto;

import static org.junit.jupiter.api.Assertions.*;

class OutRecipeSearchDtoMapperTest {

    OutRecipeSearchDtoMapper mapper = Mappers.getMapper(OutRecipeSearchDtoMapper.class);

    @Test
    void toInRecipeSearchDto() {
        // given
        long recipeId = 1L;
        String recipeName = "recipeName";
        String recipeImageName = "recipeImageName";
        double scoreAvg = 4.3;
        int views = 2;
        OutRecipeSearchDto outDto = new OutRecipeSearchDto(recipeId, recipeName, recipeImageName, scoreAvg, views);
        String recipeImage = "recipeImage";
        // when
        RecipeSearchDto result = mapper.toInRecipeSearchDto(outDto, recipeImage);
        // then
        RecipeSearchDto expected = RecipeSearchDto.builder()
                .recipeId(recipeId)
                .recipeName(recipeName)
                .recipeImage(recipeImage)
                .scoreAvg(scoreAvg)
                .views(views)
                .build();
        assertEquals(expected, result);
    }
}
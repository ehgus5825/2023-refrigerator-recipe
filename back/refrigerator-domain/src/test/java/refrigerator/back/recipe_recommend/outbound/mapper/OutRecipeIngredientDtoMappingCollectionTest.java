package refrigerator.back.recipe_recommend.outbound.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecipeIngredientDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OutRecipeIngredientDtoMappingCollectionTest {

    OutRecommendRecipeDtoMapper mapper = Mappers.getMapper(OutRecommendRecipeDtoMapper.class);

    @Test
    void getRecipeIngredientMap() {
        // given
        List<OutRecipeIngredientDto> recipeIngredients = Arrays.asList(
                new OutRecipeIngredientDto(1L, "name1", "주재료", 50.0, "unit1"),
                new OutRecipeIngredientDto(1L, "name2", "부재료", 51.0, "unit2"),
                new OutRecipeIngredientDto(2L, "name3", "양념", 52.0, "unit3"),
                new OutRecipeIngredientDto(2L, "name4", "주재료", 53.0, "unit4"));
        // when
        OutRecipeIngredientDtoMappingCollection collection = new OutRecipeIngredientDtoMappingCollection(recipeIngredients);
        Map<Long, RecommendRecipeIngredientMap> result = collection.getRecipeIngredientMap(mapper);
        // then
        assertEquals(5, result.get(1L).getIngredientTypeWeightAmount());
        assertEquals(4, result.get(2L).getIngredientTypeWeightAmount());
    }
}
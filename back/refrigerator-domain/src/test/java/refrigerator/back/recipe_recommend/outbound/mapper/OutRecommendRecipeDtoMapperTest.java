package refrigerator.back.recipe_recommend.outbound.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredient;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutMyIngredientDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecipeIngredientDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecommendRecipeDto;

import static org.junit.jupiter.api.Assertions.*;

class OutRecommendRecipeDtoMapperTest {

    OutRecommendRecipeDtoMapper mapper = Mappers.getMapper(OutRecommendRecipeDtoMapper.class);

    @Test
    void toMyIngredientDto() {
        // given
        String name = "name";
        double volume = 40.0;
        String unit = "unit";
        // when
        OutMyIngredientDto outDto = new OutMyIngredientDto(name, volume, unit);
        MyIngredientDto result = mapper.toMyIngredientDto(outDto);
        // then
        MyIngredientDto expected = MyIngredientDto.builder()
                .name(name)
                .volume(volume)
                .unit(unit).build();
        assertEquals(expected, result);
    }

    @Test
    void toRecipeIngredientDto() {
        // given
        Long recipeId = 1L;
        String name = "name";
        String typeName = "주재료";
        double volume = 40.0;
        String unit = "unit";
        // when
        OutRecipeIngredientDto outDto = new OutRecipeIngredientDto(recipeId, name, typeName, volume, unit);
        RecommendRecipeIngredient result = mapper.toRecipeIngredientDto(outDto);
        // then
        RecommendRecipeIngredient expected = RecommendRecipeIngredient.builder()
                .recipeId(recipeId)
                .name(name)
                .unit(unit)
                .volume(volume)
                .type(RecipeIngredientType.MAIN)
                .build();
        assertEquals(expected, result);
    }

    @Test
    void toRecommendRecipeDto(){
        // given
        long recipeId = 1L;
        String recipeName = "recipeName";
        String recipeImageName = "recipeImageName";
        double scoreAvg = 3.2;
        OutRecommendRecipeDto outDto = new OutRecommendRecipeDto(recipeId, recipeName, recipeImageName, scoreAvg);
        // when
        String recipeImage = "recipeImage";
        double percent = 60.0;
        RecommendRecipeDto result = mapper.toRecommendRecipeDto(outDto, recipeImage, percent);
        // then
        RecommendRecipeDto expected = RecommendRecipeDto.builder()
                .recipeId(recipeId)
                .recipeName(recipeName)
                .recipeImage(recipeImage)
                .percent("60.00")
                .scoreAvg(scoreAvg)
                .build();
        assertEquals(expected, result);
    }

}
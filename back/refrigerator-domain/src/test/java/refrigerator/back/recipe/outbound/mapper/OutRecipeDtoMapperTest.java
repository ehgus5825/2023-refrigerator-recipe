package refrigerator.back.recipe.outbound.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeCourseDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeIngredientDto;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class OutRecipeDtoMapperTest {

    OutRecipeDtoMapper mapper = Mappers.getMapper(OutRecipeDtoMapper.class);
    Pattern kcalRegex;
    Pattern cookingTimeRegex;
    Pattern servingsRegex;

    @BeforeEach
    void init(){
        kcalRegex = Pattern.compile("[0-9]+kcal");
        cookingTimeRegex = Pattern.compile("[0-9]+분");
        servingsRegex = Pattern.compile("[0-9]+인분");
    }

    @Test
    void toRecipeDto() {
        OutRecipeDto outRecipeDto = new OutRecipeDto(
                1L,
                "recipeName",
                "recipeImageName",
                4.5,
                "description",
                40,
                40,
                2,
                "difficulty");
        String recipeImage = "recipeImage";
        RecipeDto result = mapper.toRecipeDto(outRecipeDto, recipeImage);
        assertNotEquals(RecipeDto.builder().build(), result);
        assertEquals(recipeImage, result.getRecipeImage());
        assertTrue(kcalRegex.matcher(result.getKcal()).matches());
        assertTrue(cookingTimeRegex.matcher(result.getCookingTime()).matches());
        assertTrue(servingsRegex.matcher(result.getServings()).matches());
    }

    @Test
    void toRecipeIngredientDto(){
        OutRecipeIngredientDto outIngredientDto = new OutRecipeIngredientDto(
                1L,
                "name",
                "volume",
                4.3,
                "transUnit",
                "type");
        RecipeIngredientDto result = mapper.toRecipeIngredientDto(outIngredientDto);
        assertNull(result.getIsOwned());
        assertNotEquals(RecipeIngredientDto.builder().build(), result);
    }

    @Test
    void toRecipeCourseDto(){
        OutRecipeCourseDto outRecipeCourseDto = new OutRecipeCourseDto(
                1L,
                1,
                "explanation",
                "imageName");
        String courseImage = "courseImage";
        RecipeCourseDto result = mapper.toRecipeCourseDto(outRecipeCourseDto, courseImage);
        assertNotEquals(RecipeCourseDto.builder().build(), result);
        assertEquals(courseImage, result.getCourseImage());
    }

}
package refrigerator.back.recipe.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeCourseDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeIngredientDto;

@Mapper(componentModel = "spring")
public interface OutRecipeDtoMapper {

    OutRecipeDtoMapper INSTANCE = Mappers.getMapper(OutRecipeDtoMapper.class);

    @Mappings({
            @Mapping(source = "dto.cookingTime", target = "cookingTime", qualifiedByName = "addCookingTimeUnit"),
            @Mapping(source = "dto.kcal", target = "kcal", qualifiedByName = "addKcalUnit"),
            @Mapping(source = "dto.servings", target = "servings", qualifiedByName = "addServingsUnit")
    })
    RecipeDto toRecipeDto(OutRecipeDto dto, String recipeImage);

    @Mapping(target = "isOwned", ignore = true)
    RecipeIngredientDto toRecipeIngredientDto(OutRecipeIngredientDto dto);

    RecipeCourseDto toRecipeCourseDto(OutRecipeCourseDto dto, String courseImage);

    @Named("addKcalUnit")
    static String addKcalUnit(Integer kcal){
        return kcal + "kcal";
    }

    @Named("addServingsUnit")
    static String addServingsUnit(Integer servings){
        return servings + "인분";
    }

    @Named("addCookingTimeUnit")
    static String addCookingTimeUnit(Integer cookingTime){
        return cookingTime + "분";
    }
    
}

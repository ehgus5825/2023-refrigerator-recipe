package refrigerator.back.recipe_recommend.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredient;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutMyIngredientDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecipeIngredientDto;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecommendRecipeDto;

@Mapper(componentModel = "spring")
public interface OutRecommendRecipeDtoMapper {

    OutRecommendRecipeDtoMapper mapper = Mappers.getMapper(OutRecommendRecipeDtoMapper.class);

    MyIngredientDto toMyIngredientDto(OutMyIngredientDto dto);

    @Mapping(target = "type", source = "dto.typeName", qualifiedByName = "getRecipeIngredientType")
    RecommendRecipeIngredient toRecipeIngredientDto(OutRecipeIngredientDto dto);

    @Mapping(target = "percent", source = "percent", qualifiedByName = "toStringPercent")
    RecommendRecipeDto toRecommendRecipeDto(OutRecommendRecipeDto dto, String recipeImage, Double percent);

    @Named("toStringPercent")
    static String toStringPercent(Double percent){
        return String.format("%.2f", percent);
    }

    @Named("getRecipeIngredientType")
    static RecipeIngredientType getRecipeIngredientType(String typeName){
        return RecipeIngredientType.getType(typeName);
    }

}

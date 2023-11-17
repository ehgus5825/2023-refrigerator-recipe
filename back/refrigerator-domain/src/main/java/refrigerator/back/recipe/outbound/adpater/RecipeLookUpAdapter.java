package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;
import refrigerator.back.recipe.outbound.mapper.OutRecipeDtoMapper;
import refrigerator.back.recipe.outbound.repository.query.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.port.out.FindRecipeInfoPort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeLookUpAdapter implements FindRecipeInfoPort {

    private final RecipeSelectQueryRepository repository;
    private final OutRecipeDtoMapper mapper;
    private final ImageUrlConvert imageUrlConvert;


    @Override
    public RecipeDto findRecipeDto(Long recipeId) {
        return repository.selectRecipeDto(recipeId).mapping(mapper, imageUrlConvert);
    }

    @Override
    public List<RecipeIngredientDto> findRecipeIngredientDtos(Long recipeId) {
        return repository.selectRecipeIngredientList(recipeId).stream()
                .map(ingredient -> ingredient.mapping(mapper))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeCourseDto> findRecipeCourseDtos(Long recipeId) {
        return repository.selectRecipeCourseList(recipeId).stream()
                .map(course -> course.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
    }
}

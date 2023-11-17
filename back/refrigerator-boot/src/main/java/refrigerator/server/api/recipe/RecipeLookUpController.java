package refrigerator.server.api.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.mybookmark.application.port.in.CheckBookmarkedUseCase;
import refrigerator.back.myscore.application.port.in.CheckCookingUseCase;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;
import refrigerator.back.recipe.application.port.in.AddRecipeViewUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeInfoUseCase;
import refrigerator.server.api.recipe.dto.InRecipeDto;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeLookUpController {

    private final FindRecipeInfoUseCase findRecipeInfoUseCase;
    private final AddRecipeViewUseCase addRecipeViewUseCase;
    private final CheckCookingUseCase checkCookingUseCase;
    private final CheckBookmarkedUseCase checkBookmarkedUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/recipe/{recipeId}")
    public InRecipeDto findRecipeOne(@PathVariable("recipeId") Long recipeId,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        String email = getMemberEmailUseCase.getMemberEmail();
        RecipeDto recipeInfo = findRecipeInfoUseCase.findRecipeDto(recipeId);
        List<RecipeIngredientDto> ingredients = findRecipeInfoUseCase.findRecipeIngredientDtoList(recipeId, email);
        List<RecipeCourseDto> courses = findRecipeInfoUseCase.findRecipeCourseDtoList(recipeId);
        Boolean isCooked = checkCookingUseCase.isCooked(recipeId, email);
        Boolean isBookmarked = checkBookmarkedUseCase.isBookmarked(recipeId, email);
        addToRecipeView(recipeId, request, response);
        return new InRecipeDto(recipeInfo, ingredients, courses, isCooked, isBookmarked);
    }

    private void addToRecipeView(Long recipeId, HttpServletRequest request, HttpServletResponse response) {
        RecipeViewsCookie cookie = new RecipeViewsCookie();
        boolean isViewed = cookie.isViewed(recipeId, request.getCookies());
        if (!isViewed){
            response.addCookie(cookie.create(recipeId));
        }
        addRecipeViewUseCase.add(recipeId);
    }
}

package refrigerator.back.recipe.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;
import refrigerator.back.recipe.outbound.dto.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;

import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCourse.recipeCourse;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;

@Repository
@RequiredArgsConstructor
public class RecipeSelectQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 레시피 조회 쿼리
     * @param recipeId 레시피 Id
     * @return 레시피 Dto
     */
    public OutRecipeDto selectRecipeDto(Long recipeId){
        return queryFactory
                .select(new QOutRecipeDto(
                        recipe.recipeId,
                        recipe.recipeName,
                        recipe.image,
                        recipeScore.scoreAvg,
                        recipe.description,
                        recipe.cookingTime,
                        recipe.kcal,
                        recipe.servings,
                        recipe.difficulty))
                .from(recipe)
                .leftJoin(recipeScore).on(recipeScore.recipeId.eq(recipe.recipeId))
                .where(recipe.recipeId.eq(recipeId))
                .fetchOne();
    }

    /**
     * 레시피 식재료 목록 조회 쿼리
     * @param recipeId 레시피 id
     * @return 레시피 식재료 Dto 목록
     */
    public List<OutRecipeIngredientDto> selectRecipeIngredientList(Long recipeId){
        return queryFactory.select(new QOutRecipeIngredientDto(
                recipeIngredient.ingredientId,
                recipeIngredient.name,
                recipeIngredient.volume,
                recipeIngredient.transVolume,
                recipeIngredient.transUnit,
                recipeIngredient.type))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeId.eq(recipeId))
                .fetch();
    }

    /**
     * 레시피 과정 목록 조회 쿼리
     * @param recipeId 레시피 Id
     * @return 레시피 과정 Dto 목록
     */
    public List<OutRecipeCourseDto> selectRecipeCourseList(Long recipeId){
        return queryFactory.select(new QOutRecipeCourseDto(
                recipeCourse.courseId,
                recipeCourse.step,
                recipeCourse.explanation,
                recipeCourse.imageName))
                .from(recipeCourse)
                .where(recipeCourse.recipeId.eq(recipeId))
                .orderBy(recipeCourse.step.asc())
                .fetch();
    }

}

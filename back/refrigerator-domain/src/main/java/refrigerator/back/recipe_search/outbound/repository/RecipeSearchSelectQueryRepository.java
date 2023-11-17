package refrigerator.back.recipe_search.outbound.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.application.domain.entity.RecipeCategory;
import refrigerator.back.recipe.application.domain.entity.RecipeFoodType;

import refrigerator.back.recipe.outbound.dto.QOutRecipeDto;
import refrigerator.back.recipe_search.outbound.dto.OutRecipeSearchDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.outbound.dto.QOutRecipeSearchDto;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCategory.recipeCategory;
import static refrigerator.back.recipe.application.domain.entity.QRecipeFoodType.recipeFoodType;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;

@Component
@RequiredArgsConstructor
public class RecipeSearchSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OutRecipeSearchDto> selectSearchRecipeDtos(RecipeSearchCondition condition, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutRecipeSearchDto(
                        recipe.recipeId, recipe.recipeName, recipe.image,
                        recipeScore.scoreAvg,
                        recipeViews.views))
                .from(recipe)
                .leftJoin(recipeScore).on(recipeScore.recipeId.eq(recipe.recipeId))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeId))
                .leftJoin(recipeBookmark).on(recipeBookmark.recipeID.eq(recipe.recipeId))
                .leftJoin(recipeFoodType).on(recipeFoodType.typeID.eq(recipe.recipeFoodType))
                .leftJoin(recipeCategory).on(recipeCategory.categoryID.eq(recipe.recipeCategory))
                .where(
                        recipeTypeEq(condition.getRecipeType()),
                        recipeDifficultyEq(condition.getDifficulty()),
                        recipeScoreGoe(condition.getScore()),
                        recipeFoodTypeEq(condition.getRecipeFoodType()),
                        recipeCategoryEq(condition.getCategory()),
                        isContain(condition.getSearchWord())
                )
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<RecipeFoodType> selectRecipeFoodTypes() {
        return jpaQueryFactory
                .selectFrom(recipeFoodType)
                .fetch();
    }

    public List<RecipeCategory> selectRecipeCategories() {
        return jpaQueryFactory
                .selectFrom(recipeCategory)
                .fetch();
    }


    private BooleanExpression recipeCategoryEq(String category) {
        if (category != null){
            if (hasText(category)){
                return recipeCategory.categoryName.eq(category);
            }
        }
        return null;
    }

    private BooleanExpression isContain(String searchWord){
        if (searchWord != null){
            if (hasText(searchWord)){
                return isKeywordInRecipeIngredient(searchWord).or(recipeNameContains(searchWord));
            }
        }
        return null;
    }

    private BooleanExpression isKeywordInRecipeIngredient(String searchWord) {
        return searchWord != null ? recipe.recipeId.in(JPAExpressions.select(recipeIngredient.recipeId)
                .from(recipeIngredient)
                .where(recipeIngredient.name.eq(searchWord))) : null;
    }

    private BooleanExpression recipeNameContains(String searchWord) {
        return recipe.recipeName.contains(searchWord);
    }

    private BooleanExpression recipeScoreGoe(Double score) {
        return score != null ? recipeScore.scoreAvg.goe(score) : null;
    }

    private BooleanExpression recipeDifficultyEq(String difficulty) {
        if (difficulty != null){
            if (hasText(difficulty)){
                return recipe.difficulty.eq(difficulty);
            }
        }
        return null;
    }

    private BooleanExpression recipeFoodTypeEq(String foodType) {
        if (foodType != null){
            if (hasText(foodType)){
                return recipeFoodType.typeName.eq(foodType);
            }
        }
        return null;
    }

    private BooleanExpression recipeTypeEq(String recipeType) {
        if (recipeType != null){
            if (hasText(recipeType)){
                return recipe.recipeType.eq(recipeType);
            }
        }
        return null;
    }

}


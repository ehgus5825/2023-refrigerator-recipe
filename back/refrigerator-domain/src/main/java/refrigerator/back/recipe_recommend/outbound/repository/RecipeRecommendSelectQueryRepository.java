package refrigerator.back.recipe_recommend.outbound.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe_recommend.outbound.dto.*;

import java.time.LocalDate;
import java.util.*;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;

@Repository
@RequiredArgsConstructor
public class RecipeRecommendSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 레시피 식재료 전체 조회 쿼리
     * @return 레시피 식재료 목록
     */
    public List<OutRecipeIngredientDto> selectRecipeIngredientDtoList() {
        return jpaQueryFactory.select(
                        new QOutRecipeIngredientDto(
                                recipeIngredient.recipeId,
                                recipeIngredient.name,
                                recipeIngredient.type,
                                recipeIngredient.transVolume,
                                recipeIngredient.transUnit))
                .from(recipeIngredient)
                .fetch();
    }

    /**
     * 나의 식재료 목록 조회 쿼리
     * @param startDate 추천 대상 식재료 유통기한 범위 시작일
     * @param memberId 사용자 Id
     * @return 나의 식재료 목록 조회
     */
    public List<OutMyIngredientDto> selectMyIngredientNames(LocalDate startDate, String memberId) {
        return jpaQueryFactory.select(new QOutMyIngredientDto(
                        ingredient.name,
                        ingredient.capacity,
                        ingredient.capacityUnit))
                .from(ingredient)
                .where(ingredient.email.eq(memberId),
                        ingredient.expirationDate.goe(startDate))
                .fetch();
    }

    /**
     * 매칭율이 높은 상위 레시피에 대한 정보 조회 쿼리
     * @param ids 레시피 Id 리스트
     * @return 레시피 정보 목록
     */
    public List<OutRecommendRecipeDto> selectRecipeInfoByIds(List<Long> ids) {
        return jpaQueryFactory.select(new QOutRecommendRecipeDto(
                        recipe.recipeId,
                        recipe.recipeName,
                        recipe.image,
                        recipeScore.scoreAvg))
                .from(recipe)
                .innerJoin(recipeScore).on(recipeScore.recipeId.eq(recipe.recipeId))
                .where(recipe.recipeId.in(ids))
                .fetch();
    }

}

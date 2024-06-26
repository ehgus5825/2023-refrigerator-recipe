package refrigerator.back.recipe.outbound.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.WriteQueryResultType;

import javax.persistence.EntityManager;

import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RecipeUpdateQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 레시피 조회수 증가 쿼리
     * @param recipeId 레시피 Id
     * @return 쿼리 결과 타입
     */
    public WriteQueryResultType updateRecipeViews(Long recipeId) {
        long result = jpaQueryFactory
                .update(recipeViews)
                .set(recipeViews.views, recipeViews.views.add(1))
                .where(recipeViews.recipeID.eq(recipeId))
                .execute();

        em.flush(); //
        em.clear();

        return WriteQueryResultType.findTypeByResult(result);
    }

    /**
     *
     * @param recipeId
     * @param value
     * @return
     */
    public WriteQueryResultType updateRecipeBookmarkCount(Long recipeId, int value) {

        log.info("{}", value);

        long result = jpaQueryFactory
                .update(recipeBookmark)
                .set(recipeBookmark.count, recipeBookmark.count.add(value))
                .where(recipeBookmark.recipeID.eq(recipeId),
                        decideUpdateCondition(value))
                .execute();

        em.flush(); //
        em.clear();

        return WriteQueryResultType.findTypeByResult(result);
    }

    public BooleanExpression decideUpdateCondition(int value) {
        if(value == -1)
            return recipeBookmark.count.goe(1);
        return null;
    }

}
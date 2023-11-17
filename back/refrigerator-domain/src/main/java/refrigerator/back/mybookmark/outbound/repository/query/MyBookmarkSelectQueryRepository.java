package refrigerator.back.mybookmark.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.mybookmark.outbound.dto.*;


import java.util.List;

import static refrigerator.back.mybookmark.application.domain.QMyBookmark.myBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;

@Repository
@RequiredArgsConstructor
public class MyBookmarkSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 나의 북마크 미리보기 조회 쿼리
     * @param memberId 사용자 id (이메일)
     * @param pageable 페이징 처리 정보
     * @return 나의 북마크 목록
     */
    public List<OutMyBookmarkPreviewDto> selectMyBookmarkPreviews(String memberId, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutMyBookmarkPreviewDto(
                        myBookmark.bookmarkId,
                        myBookmark.recipeId,
                        recipe.image,
                        recipe.recipeName,
                        myBookmark.createDateTime))
                .from(myBookmark)
                .leftJoin(recipe).on(recipe.recipeId.eq(myBookmark.recipeId))
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false))
                .orderBy(myBookmark.createDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 나의 북마크 전체 개수 조회
     * @param memberId 사용자 id (이메일)
     * @return 북마크 개수
     */
    public OutMyBookmarkNumberDto selectNumberOfMyBookmarks(String memberId){
        Long number = jpaQueryFactory.select(myBookmark.count())
                .from(myBookmark)
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false))
                .fetchOne();
        return new OutMyBookmarkNumberDto(number);
    }

    /**
     * 나의 북마크 상세 조회
     * @param memberId
     * @param pageable
     * @return
     */
    public List<OutMyBookmarkDto> selectMyBookmarks(String memberId, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutMyBookmarkDto(
                        myBookmark.bookmarkId,
                        myBookmark.recipeId,
                        recipe.image,
                        recipe.recipeName,
                        recipeScore.scoreAvg,
                        recipeViews.views,
                        myBookmark.createDateTime))
                .from(myBookmark)
                .leftJoin(recipe).on(recipe.recipeId.eq(myBookmark.recipeId))
                .leftJoin(recipeScore).on(recipeScore.recipeId.eq(myBookmark.recipeId))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(myBookmark.recipeId))
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.deleted.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(myBookmark.createDateTime.desc())
                .fetch();
    }

    /**
     * 동일한 recipeId 와 memberId 북마크 개수 조회
     * @param recipeId 레시피 id
     * @param memberId 사용자 id
     * @return 북마크 개수
     */
    public OutMyBookmarkNumberDto selectNumberOfMyBookmarkByRecipeIdAndMemberId(Long recipeId, String memberId){
        Long number = jpaQueryFactory.select(myBookmark.count())
                .from(myBookmark)
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.recipeId.eq(recipeId),
                        myBookmark.deleted.eq(false))
                .fetchOne();
        return new OutMyBookmarkNumberDto(number);
    }
    
}

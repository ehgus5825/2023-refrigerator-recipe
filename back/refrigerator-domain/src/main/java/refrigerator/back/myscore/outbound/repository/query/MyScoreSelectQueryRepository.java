package refrigerator.back.myscore.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.application.domain.QMyScore;
import refrigerator.back.myscore.outbound.dto.*;
import refrigerator.back.recipe.application.domain.entity.QRecipe;

import java.util.List;
import java.util.Objects;

import static refrigerator.back.myscore.application.domain.QMyScore.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.*;

@Repository
@RequiredArgsConstructor
public class MyScoreSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 나의 별점 조회
     * @param recipeId 별점을 남긴 레시피 Id
     * @param memberId 사용자 id(이메일)
     * @return OutMyScoreDto (별점 식별자 값, 별점)
     */
    public OutMyScoreDto selectMyScoreDto(Long recipeId, String memberId){
        return jpaQueryFactory.select(new QOutMyScoreDto(myScore.scoreId, myScore.score))
                .from(myScore)
                .where(myScore.recipeId.eq(recipeId),
                        myScore.memberId.eq(memberId))
                .fetchOne();
    }

    /**
     *  나의 별점 상세 조회
     * @param memberId 사용자 Id (이메일)
     * @param page 페이징 처리 정보
     * @return 나의 별점 목록
     */
    public List<OutMyScoreDetailDto> selectMyScoreDetailDtos(String memberId, Pageable page){
        return jpaQueryFactory.select(new QOutMyScoreDetailDto(
                        myScore.scoreId,
                        myScore.recipeId,
                        recipe.image,
                        recipe.recipeName,
                        myScore.score,
                        myScore.createDateTime))
                .from(myScore)
                .leftJoin(recipe).on(recipe.recipeId.eq(myScore.recipeId))
                .where(myScore.memberId.eq(memberId))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .orderBy(myScore.createDateTime.desc())
                .fetch();
    }

    /**
     * 나의 별점 미리보기 조회
     * @param memberId 사용자 Id (이메일)
     * @param page 페이징 처리 정보
     * @return 나의 별점 미리보기 목록
     */
    public List<OutMyScorePreviewDto> selectMyScorePreviewDtos(String memberId, Pageable page){
        return jpaQueryFactory.select(new QOutMyScorePreviewDto(
                        myScore.scoreId,
                        myScore.recipeId,
                        recipe.image,
                        recipe.recipeName,
                        myScore.createDateTime))
                .from(myScore)
                .leftJoin(recipe).on(recipe.recipeId.eq(myScore.recipeId))
                .where(myScore.memberId.eq(memberId))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .orderBy(myScore.createDateTime.desc())
                .fetch();
    }

    /**
     * 나의 별점 전체 개수 조회
     * @param memberId 사용자 id (이메일)
     * @return 나의 별점 전체 개수
     */
    public OutMyScoreNumberDto selectMyScoreCountByMemberId(String memberId){
        Long number = jpaQueryFactory.select(myScore.count())
                .from(myScore)
                .where(myScore.memberId.eq(memberId))
                .fetchOne();
        return new OutMyScoreNumberDto(number);
    }

    /**
     * 나의 별점 개수 조회
     * @param memberId 사용자 id (이메일)
     * @param recipeId 레시피 id
     * @return 나의 별점 개수
     */
    public OutMyScoreNumberDto selectMyScoreCountByMemberIdAndRecipeId(String memberId, Long recipeId){
        Long number = jpaQueryFactory.select(myScore.count())
                .from(myScore)
                .where(myScore.memberId.eq(memberId),
                        myScore.recipeId.eq(recipeId))
                .fetchOne();
        return new OutMyScoreNumberDto(number);
    }
}


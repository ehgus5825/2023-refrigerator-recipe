package refrigerator.back.mybookmark.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.jpa.WriteQueryResultType;

import javax.persistence.EntityManager;

import static refrigerator.back.mybookmark.application.domain.QMyBookmark.myBookmark;


@Repository
@RequiredArgsConstructor
public class MyBookmarkUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 북마크 삭제 상태 변경 쿼리
     * @param bookmarkId 북마크 Id
     * @return 쿼리 실행 결과 타입
     */
    public WriteQueryResultType updateMyBookmarkDeletedStatusById(Long bookmarkId, Boolean status) {
        long result = jpaQueryFactory.update(myBookmark)
                .set(myBookmark.deleted, status)
                .where(myBookmark.bookmarkId.eq(bookmarkId))
                .execute();
        em.flush();
        em.clear();
        return WriteQueryResultType.findTypeByResult(result);
    }
}

package refrigerator.back.mybookmark.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.mybookmark.application.domain.QMyBookmark;

import javax.persistence.EntityManager;

import static refrigerator.back.mybookmark.application.domain.QMyBookmark.*;

@Repository
@RequiredArgsConstructor
public class MyBookmarkDeleteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 북마크 삭제 쿼리, deleted 상태가 true 인 데이터 전체 삭제 됨
     * @return 쿼리 실행 결과 타입
     */
    public WriteQueryResultType deleteMyBookmarkByDeletedStatus(){
        long result = jpaQueryFactory.delete(myBookmark)
                .where(myBookmark.deleted.eq(true))
                .execute();
        em.flush();
        em.clear();
        return WriteQueryResultType.findTypeByResult(result);
    }

}

package refrigerator.back.member.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.domain.QMember;

import javax.persistence.EntityManager;

import static refrigerator.back.global.jpa.WriteQueryResultType.*;
import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberDeleteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;


    public WriteQueryResultType deleteMemberByStatus(MemberStatus status){
        long result = jpaQueryFactory.delete(member)
                .where(member.memberStatus.eq(status))
                .execute();
        clear();
        return findTypeByResult(result);
    }

    private void clear() {
        em.flush();
        em.clear();
    }
}

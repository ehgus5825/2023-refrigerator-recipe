package refrigerator.back.member.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.outbound.dto.OutMemberDto;
import refrigerator.back.member.outbound.dto.QOutMemberDto;

import java.util.Objects;

import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 같은 이메일을 가진 회원 수 조회
     * @param email 사용자 이메일
     * @return 회원 수
     */
    public Integer selectCountOfMemberByEmail(String email){
        return Objects.requireNonNull(jpaQueryFactory.select(member.count())
                .from(member)
                .where(member.email.eq(email))
                .fetchOne()).intValue();
    }

    /**
     * 회원 Dto (닉네임, 프로필 이미지명) 조회
     * @param email 사용자 이메일
     * @return 회원 Dto
     */
    public OutMemberDto selectMemberDtoByEmail(String email){
        return jpaQueryFactory.select(new QOutMemberDto(member.nickname, member.profile.stringValue()))
                .from(member)
                .where(member.email.eq(email),
                        member.memberStatus.eq(MemberStatus.STEADY_STATUS))
                .fetchOne();
    }
}

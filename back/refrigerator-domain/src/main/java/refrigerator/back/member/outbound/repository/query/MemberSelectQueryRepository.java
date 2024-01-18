package refrigerator.back.member.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.back.member.outbound.dto.OutMemberDto;
import refrigerator.back.member.outbound.dto.QOutMemberDto;

import static refrigerator.back.member.application.domain.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

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

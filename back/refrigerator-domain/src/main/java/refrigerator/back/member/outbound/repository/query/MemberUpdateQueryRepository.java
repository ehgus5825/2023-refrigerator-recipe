package refrigerator.back.member.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

import javax.persistence.EntityManager;

import static refrigerator.back.global.jpa.WriteQueryResultType.*;
import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 닉네임 수정 쿼리
     * @param email 사용자 이메일
     * @param nickname 변경할 닉네임
     * @return 쿼리 실행 결과, 1-> 성공 / 1 초과 -> 중복된 회원 존재 / 1 미만 -> 회원을 찾을 수 없음
     */
    public WriteQueryResultType updateNicknameByEmail(String email, String nickname){
        long result = jpaQueryFactory.update(member)
                .set(member.nickname, nickname)
                .where(member.email.eq(email))
                .execute();
        clear();
        return findTypeByResult(result);
    }

    /**
     * 비밀번호 변경 쿼리
     * @param email 사용자 이메일
     * @param password 암호화된 새로운 비밀번호
     * @return 쿼리 실행 결과, 1-> 성공 / 1 초과 -> 중복된 회원 존재 / 1 미만 -> 회원을 찾을 수 없음
     */
    public WriteQueryResultType updatePasswordByEmail(String email, String password){
        long result = jpaQueryFactory.update(member)
                .set(member.password, password)
                .where(member.email.eq(email))
                .execute();
        clear();
        return findTypeByResult(result);
    }

    /**
     * 프로필 이미지 변경 쿼리
     * @param email 사용자 이메일
     * @param profileImage 변경할 프로필 이미지 타입
     * @return 쿼리 실행 결과, 1-> 성공 / 1 초과 -> 중복된 회원 존재 / 1 미만 -> 회원을 찾을 수 없음
     */
    public WriteQueryResultType updateProfileImageByEmail(String email, MemberProfileImage profileImage){
        long result = jpaQueryFactory.update(member)
                .set(member.profile, profileImage)
                .where(member.email.eq(email))
                .execute();
        clear();
        return findTypeByResult(result);
    }

    /**
     * 회원 상태 변경 쿼리
     * @param email 사용자 이메일
     * @param status 변경할 회원 상태
     * @return 쿼리 실행 결과, 1-> 성공 / 1 초과 -> 중복된 회원 존재 / 1 미만 -> 회원을 찾을 수 없음
     */
    public WriteQueryResultType updateMemberStatusByEmail(String email, MemberStatus status){
        long result = jpaQueryFactory.update(member)
                .set(member.memberStatus, status)
                .where(member.email.eq(email))
                .execute();
        clear();
        return findTypeByResult(result);
    }

    private void clear() {
        em.flush();
        em.clear();
    }
}

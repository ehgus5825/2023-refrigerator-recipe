package refrigerator.back.member.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, MemberUpdateQueryRepository.class})
class MemberUpdateQueryRepositoryTest {

    @Autowired MemberUpdateQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("닉네임 수정 쿼리 성공 테스트")
    void updateNicknameByEmail() {
        // given
        String email = "email";
        String nickname = "닉네임";
        LocalDateTime joinDateTime = LocalDateTime.of(2023, 7, 12, 1, 47);
        Member member = Member.createForTest(
                email,
                "password",
                "nickname",
                MemberStatus.STEADY_STATUS,
                MemberProfileImage.PROFILE_IMAGE_ONE,
                joinDateTime);
        Long memberId = em.persistAndGetId(member, Long.class);
        // when
        WriteQueryResultType result = queryRepository.updateNicknameByEmail(email, nickname);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(nickname, em.find(Member.class, memberId).getNickname());
    }

    @Test
    @DisplayName("닉네임 수정 쿼리 실패 테스트")
    void updateNicknameByEmailFailTest() {
        // given
        String email = "email";
        String nickname = "닉네임";
        LocalDateTime joinDateTime = LocalDateTime.of(2023, 7, 12, 1, 47);
        Member member = Member.createForTest(
                email,
                "password",
                "nickname",
                MemberStatus.STEADY_STATUS,
                MemberProfileImage.PROFILE_IMAGE_ONE,
                joinDateTime);
        Long memberId = em.persistAndGetId(member, Long.class);
        // when
        WriteQueryResultType result = queryRepository.updateNicknameByEmail("wrongEmail", nickname);
        // then
        assertEquals(WriteQueryResultType.FAIL, result);
        assertNotEquals(nickname, em.find(Member.class, memberId).getNickname());
    }

    @Test
    @DisplayName("비밀번호 수정 쿼리 성공 테스트")
    void updatePasswordByEmail() {
        // given
        String email = "mstest102@gmail.com";
        String password = "password";
        LocalDateTime joinDateTime = LocalDateTime.of(2023, 7, 12, 1, 47);
        Member member = Member.createForTest(
                email,
                password,
                "nickname",
                MemberStatus.STEADY_STATUS,
                MemberProfileImage.PROFILE_IMAGE_ONE,
                joinDateTime);
        Long memberId = em.persistAndGetId(member, Long.class);
        // when
        WriteQueryResultType result = queryRepository.updatePasswordByEmail(email, password);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(password, em.find(Member.class, memberId).getPassword());
    }

    @Test
    @DisplayName("프로필 수정 쿼리 성공 테스트")
    void updateProfileImageByEmail() {
        // given
        String email = "mstest102@gmail.com";
        MemberProfileImage profileImage = MemberProfileImage.PROFILE_IMAGE_ONE;
        LocalDateTime joinDateTime = LocalDateTime.of(2023, 7, 12, 1, 47);
        Member member = Member.createForTest(
                email,
                "password",
                "nickname",
                MemberStatus.STEADY_STATUS,
                MemberProfileImage.PROFILE_IMAGE_TWO,
                joinDateTime);
        Long memberId = em.persistAndGetId(member, Long.class);
        // when
        WriteQueryResultType result = queryRepository.updateProfileImageByEmail(email, profileImage);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(profileImage, em.find(Member.class, memberId).getProfile());
    }
}
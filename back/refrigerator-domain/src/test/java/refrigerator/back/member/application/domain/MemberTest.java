package refrigerator.back.member.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void join() {
        // given
        LocalDateTime joinDateTime = LocalDateTime.of(2023, 7, 12, 1, 1);
        // when
        Member member = Member.join("email", "password", "nickname", 1, joinDateTime);
        // then
        assertEquals(MemberStatus.STEADY_STATUS, member.getMemberStatus());
        assertEquals(MemberProfileImage.PROFILE_IMAGE_TWO, member.getProfile());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 -> 랜덤 프로필 이미지를 찾을 수 없을 때")
    void joinFailTest() {
        // given
        LocalDateTime joinDateTime = LocalDateTime.of(2023, 7, 12, 1, 1);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                Member.join("email", "password", "nickname", 6, joinDateTime);
            }catch (BusinessException e){
                assertEquals(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("중복된 회원 있는지 확인 성공 테스트, number = 같은 이메일을 가진 회원 수")
    void isDuplicated() {
        int number = 0;
        assertDoesNotThrow(() -> Member.isDuplicatedByEmail(number));
    }

    @Test
    @DisplayName("중복된 회원 있는지 확인 실패 테스트, number = 같은 이메일을 가진 회원 수")
    void isDuplicatedFailTEst() {
        int number = 1;
        assertThrows(BusinessException.class, () -> Member.isDuplicatedByEmail(number));
    }
}
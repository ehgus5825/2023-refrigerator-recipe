package refrigerator.back.comment.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.common.RandomUUID;
import refrigerator.back.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class CommentHeartPeopleTest {

    @Test
    @DisplayName("사용자가 좋아요를 눌렀을 때 생성 테스트")
    void create() {
        RandomUUID randomUUID = () -> "1234567890";
        boolean isExistPeople = false;
        CommentHeartPeople people = CommentHeartPeople.add(isExistPeople, randomUUID, 1L, "memberId");
        String expected = "12345678";
        assertEquals(people.getId(), expected);
    }

    @Test
    @DisplayName("사용자가 좋아요를 눌렀을 때 생성 실패 테스트 -> 중복된 증가 요청일 때")
    void checkAddRequest() {
        RandomUUID randomUUID = () -> "1234567890";
        boolean isExistPeople = true;
        assertThrows(BusinessException.class, () -> CommentHeartPeople.add(isExistPeople, randomUUID, 1L, "memberId"));
    }

    @Test
    @DisplayName("사용자의 좋아요 기록을 삭제하기 전 -> 중복 감소 요청인지 확인")
    void checkReduceRequest() {
        boolean isExistPeople = false;
        assertThrows(BusinessException.class, () -> CommentHeartPeople.checkReduceRequest(isExistPeople));
    }

}
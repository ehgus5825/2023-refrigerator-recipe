package refrigerator.back.comment.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommentDtoTest {


    @Test
    @DisplayName("특정 댓글에 대해 사용자가 하트를 눌렀는지 확인 테스트 -> 하트를 눌렀을 때")
    void isLikedTest1() {
        // given
        long expectedId = 1L;
        CommentDto commentDto = new CommentDto(
                expectedId,
                "nickname",
                1,
                "createDate",
                false,
                "content",
                null);
        Map<Long, CommentHeartPeopleDto> people = getMap(expectedId);
        // when
        commentDto.isLiked(people);
        // then
        assertTrue(commentDto.getLikedState());
        assertNotNull(commentDto.getLikedInfo());
    }

    @Test
    @DisplayName("특정 댓글에 대해 사용자가 하트를 눌렀는지 확인 테스트 -> 하트를 누르지 않았을 때")
    void isLikedTest2() {
        // given
        long expectedId = 1L;
        long unExpectedId = 2L;
        CommentDto commentDto = new CommentDto(
                unExpectedId,
                "nickname",
                1,
                "createDate",
                false,
                "content",
                null);
        Map<Long, CommentHeartPeopleDto> people = getMap(expectedId);
        // when
        commentDto.isLiked(people);
        // then
        assertFalse(commentDto.getLikedState());
        assertNull(commentDto.getLikedInfo());
    }

    private Map<Long, CommentHeartPeopleDto> getMap(long expectedId){
        Map<Long, CommentHeartPeopleDto> map = new HashMap<>();
        map.put(expectedId, new CommentHeartPeopleDto("peopleId", expectedId));
        return map;
    }
}
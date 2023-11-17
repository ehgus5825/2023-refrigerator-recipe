package refrigerator.back.comment.outbound.mapper;

import org.junit.jupiter.api.Test;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OutCommentHeartPeopleMappingCollectionTest {

    @Test
    void makeDtoTable() {
        // given
        List<CommentHeartPeople> commentHeartPeople = Arrays.asList(
                new CommentHeartPeople("id1", 1L, "memberId"),
                new CommentHeartPeople("id2", 2L, "memberId"));
        // when
        OutCommentHeartPeopleMappingCollection collection = new OutCommentHeartPeopleMappingCollection(commentHeartPeople);
        Map<Long, CommentHeartPeopleDto> result = collection.getPeopleMap();
        // then
        assertTrue(result.containsKey(1L));
        assertTrue(result.containsKey(2L));
        assertEquals("id1", result.get(1L).getPeopleId());
        assertEquals("id2", result.get(2L).getPeopleId());
    }
}
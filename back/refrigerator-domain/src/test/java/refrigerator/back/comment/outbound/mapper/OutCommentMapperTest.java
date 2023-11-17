package refrigerator.back.comment.outbound.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.outbound.dto.OutCommentDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OutCommentMapperTest {

    OutCommentMapper mapper = Mappers.getMapper(OutCommentMapper.class);

    @Test
    void toCommentDto() {
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 12, 1, 1);
        OutCommentDto outDto = new OutCommentDto(1L, "nickname", 1, createDateTime, false, "content");
        String createDate = "createDate";
        CommentDto result = mapper.toCommentDto(outDto, createDate);
        assertEquals(createDate, result.getCreateDate());
        assertFalse(result.getLikedState());
        assertNull(result.getLikedInfo());
    }
}
package refrigerator.back.notification.outbound.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.outbound.dto.OutCommentNotificationDTO;

import static org.assertj.core.api.Assertions.*;

class OutNotificationMapperTest {

    OutNotificationMapper outNotificationMapper = Mappers.getMapper(OutNotificationMapper.class);

    @Test
    @DisplayName("OutCommentNotificationDTO에서 CommentNotificationDTO로 변환")
    void outNotificationMapperTest() {

        OutCommentNotificationDTO outDto = OutCommentNotificationDTO.builder()
                .authorId("email123@gmail.com")
                .recipeId(1L)
                .build();

        CommentNotificationDTO dto = outNotificationMapper.toCommentNotificationDetail(outDto);

        assertThat(dto.getAuthorId()).isEqualTo("email123@gmail.com");
        assertThat(dto.getRecipeId()).isEqualTo(1L);
    }
}
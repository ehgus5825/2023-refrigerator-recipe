package refrigerator.back.notification.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.time.TimeService;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.service.NotificationTimeService;
import refrigerator.back.notification.outbound.dto.OutCommentNotificationDTO;
import refrigerator.back.notification.outbound.dto.OutNotificationDTO;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("OutNotificationDTO에서 NotificationDTO로 변환")
    void toNotificationDTO() {

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        OutNotificationDTO notification = OutNotificationDTO.builder()
                .id(1L)
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .createDate(now.minusSeconds(1))
                .build();

        NotificationTimeService timeService = new TimeService(() -> now);

        NotificationDTO dto = outNotificationMapper.toNotificationDTO(notification, timeService.replace(notification.getCreateDate()));

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMessage()).isEqualTo("test message");
        assertThat(dto.getType()).isEqualTo(NotificationType.HEART);
        assertThat(dto.getRegisterTime()).isEqualTo("1 초 전");
        assertThat(dto.getPath()).isEqualTo("/");
        assertThat(dto.isReadStatus()).isEqualTo(false);
    }
}
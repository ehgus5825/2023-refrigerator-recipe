package refrigerator.back.notification.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.TimeService;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationTimeService;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.dto.NotificationDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    @DisplayName("Notification에서 NotificationDTO로 변환")
    void toNotificationDTO() {

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        Notification notification = Notification.builder()
                .id(1L)
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .memberId("email123@gmail.com")
                .method(BasicHttpMethod.GET.name())
                .createDate(now.minusSeconds(1))
                .build();

        NotificationTimeService timeService = new TimeService(() -> now);

        NotificationDTO dto = notificationMapper.toNotificationDTO(notification, timeService.replace(notification.getCreateDate()));

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMessage()).isEqualTo("test message");
        assertThat(dto.getType()).isEqualTo(NotificationType.HEART);
        assertThat(dto.getRegisterTime()).isEqualTo("1 초 전");
        assertThat(dto.getPath()).isEqualTo("/");
        assertThat(dto.isReadStatus()).isEqualTo(false);
    } 
}
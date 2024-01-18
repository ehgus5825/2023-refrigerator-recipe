package refrigerator.back.notification.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.out.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.UpdateNotificationReadStatusPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks NotificationService notificationService;

    @Mock UpdateNotificationReadStatusPort updateNotificationReadStatusPort;

    @Mock FindNotificationListPort findNotificationListPort;

    @Test
    @DisplayName("알림 조회 테스트")
    void getNotificationListTest() {
        String memberId = "email123@gmail.com";

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .id(1L)
                .message("test message")
                .type(NotificationType.HEART)
                .registerTime("1 분 전")
                .path("/")
                .readStatus(false)
                .build();

        List<NotificationDTO> notificationDTOs = new ArrayList<>(List.of(notificationDTO));

        given(findNotificationListPort.findNotificationList(memberId, 0, 1))
                .willReturn(notificationDTOs);

        assertThat(notificationService.getNotificationList(memberId, 0, 1))
                .isEqualTo(notificationDTOs);
    }

    @Test
    @DisplayName("알림 단건 조회 => 알림 단건 읽음 상태 변경 테스트")
    void modifyNotificationReadStatusTest() {
        willDoNothing().given(updateNotificationReadStatusPort)
                .updateReadStatus(1L, true);

        notificationService.modifyNotificationReadStatus(1L);

        verify(updateNotificationReadStatusPort, times(1))
                .updateReadStatus(1L, true);

    }
}
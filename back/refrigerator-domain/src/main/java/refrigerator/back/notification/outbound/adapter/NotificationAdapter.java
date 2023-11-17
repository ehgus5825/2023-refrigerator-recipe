package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.outbound.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.port.out.notification.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.notification.SaveNotificationPort;
import refrigerator.back.notification.application.port.out.notification.UpdateNotificationReadStatusPort;

import java.util.List;

import static refrigerator.back.notification.exception.NotificationExceptionType.NOTIFICATION_READ_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements UpdateNotificationReadStatusPort, SaveNotificationPort, FindNotificationListPort {

    private final NotificationQueryRepository notificationQueryRepository;
    private final NotificationPersistenceRepository notificationPersistenceRepository;

    @Override
    public List<Notification> findNotificationList(String email, int page, int size) {
        return notificationQueryRepository.findNotificationList(email, PageRequest.of(page, size));
    }

    @Override
    public void updateReadStatus(Long notificationId, boolean status) {
        if (notificationQueryRepository.updateReadStatus(notificationId, status) != 1)
            throw new BusinessException(NOTIFICATION_READ_FAIL);
    }

    @Override
    public Long saveNotification(Notification notification) {
        return notificationPersistenceRepository.save(notification).getId();
    }
}

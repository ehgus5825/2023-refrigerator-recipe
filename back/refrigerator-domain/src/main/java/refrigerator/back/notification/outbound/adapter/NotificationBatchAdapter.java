package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.outbound.repository.query.NotificationBatchQueryRepository;
import refrigerator.back.notification.application.port.batch.DeleteNotificationBatchPort;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationBatchAdapter implements DeleteNotificationBatchPort {

    private final NotificationBatchQueryRepository notificationBatchQueryRepository;

    @Override
    @Transactional
    public Long deleteNotification(Boolean option, LocalDateTime date) {
        return notificationBatchQueryRepository.deleteNotification(option, date);
    }

    @Override
    public Long deleteTestNotification(LocalDateTime date) {
        return notificationBatchQueryRepository.deleteTestNotification(date);
    }
}

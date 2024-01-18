package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.service.NotificationTimeService;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapper;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.outbound.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.port.out.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.SaveNotificationPort;
import refrigerator.back.notification.application.port.out.UpdateNotificationReadStatusPort;

import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.notification.exception.NotificationExceptionType.NOTIFICATION_READ_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements UpdateNotificationReadStatusPort, SaveNotificationPort, FindNotificationListPort {

    private final NotificationQueryRepository notificationQueryRepository;
    private final NotificationPersistenceRepository notificationPersistenceRepository;

    private final OutNotificationMapper mapper;
    private final NotificationTimeService timeService;

    @Override
    public List<NotificationDTO> findNotificationList(String email, int page, int size) {
        return notificationQueryRepository.findNotificationList(email, PageRequest.of(page, size))
                .stream().map(dto -> dto.mapping(mapper, timeService))
                .collect(Collectors.toList());
    }

    @Override
    public void updateReadStatus(Long notificationId, boolean status) {
        notificationQueryRepository.updateReadStatus(notificationId, status)
                .throwExceptionWhenNotAllowDuplicationResource(NOTIFICATION_READ_FAIL);
    }

    @Override
    public Long saveNotification(Notification notification) {
        return notificationPersistenceRepository.save(notification).getId();
    }
}

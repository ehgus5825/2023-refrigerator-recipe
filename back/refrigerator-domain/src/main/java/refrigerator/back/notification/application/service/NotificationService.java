package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.application.domain.NotificationTimeService;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.mapper.NotificationMapper;
import refrigerator.back.notification.application.port.in.notification.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.notification.ModifyNotificationReadStatusUseCase;
import refrigerator.back.notification.application.port.out.notification.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.notification.UpdateNotificationReadStatusPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements FindNotificationListUseCase, ModifyNotificationReadStatusUseCase {

    private final UpdateNotificationReadStatusPort updateNotificationReadStatusPort;
    private final NotificationTimeService notificationTimeService;
    private final FindNotificationListPort findNotificationListPort;

    private final NotificationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationList(String email, int page, int size) {
        return findNotificationListPort.findNotificationList(email, page, size)
                .stream()
                .map(notification -> mapper.toNotificationDTO(notification,
                                notificationTimeService.replace(notification.getCreateDate())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void modifyNotificationReadStatus(Long id) {
        updateNotificationReadStatusPort.updateReadStatus(id, true);
    }
}
package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ModifyNotificationReadStatusUseCase;
import refrigerator.back.notification.application.port.out.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.UpdateNotificationReadStatusPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements FindNotificationListUseCase, ModifyNotificationReadStatusUseCase {

    private final UpdateNotificationReadStatusPort updateNotificationReadStatusPort;
    private final FindNotificationListPort findNotificationListPort;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationList(String email, int page, int size) {
        return findNotificationListPort.findNotificationList(email, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getExpirationNotificationList(String email) {
        return findNotificationListPort.findExpirationNotificationList(email);
    }

    @Override
    @Transactional
    public void modifyNotificationReadStatus(Long id) {
        updateNotificationReadStatusPort.updateReadStatus(id, true);
    }
}
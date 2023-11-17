package refrigerator.back.notification.application.domain;

import java.time.LocalDateTime;

public interface NotificationTimeService {
    String replace(LocalDateTime date);
}

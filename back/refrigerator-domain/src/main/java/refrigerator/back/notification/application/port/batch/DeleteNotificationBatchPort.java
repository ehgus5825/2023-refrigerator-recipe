package refrigerator.back.notification.application.port.batch;

import java.time.LocalDateTime;

public interface DeleteNotificationBatchPort {
    Long deleteNotification(Boolean option, LocalDateTime date);

    Long deleteTestNotification(LocalDateTime date);

}

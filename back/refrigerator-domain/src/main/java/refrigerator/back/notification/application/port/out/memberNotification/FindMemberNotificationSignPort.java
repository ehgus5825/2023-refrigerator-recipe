package refrigerator.back.notification.application.port.out.memberNotification;

import java.util.Optional;

public interface FindMemberNotificationSignPort {
    Optional<Boolean> getSign(String memberId);
}

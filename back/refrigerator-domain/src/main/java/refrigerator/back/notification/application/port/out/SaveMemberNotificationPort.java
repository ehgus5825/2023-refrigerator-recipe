package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.application.domain.entity.MemberNotification;

public interface SaveMemberNotificationPort {
    String save(MemberNotification memberNotification);
}

package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.application.domain.entity.MemberNotification;

public interface FindMemberNotificationPort {
    MemberNotification getMemberNotification(String memberId);
}

package refrigerator.back.notification.application.port.in.memberNotification;


import refrigerator.back.notification.application.dto.NotificationSignDTO;

public interface FindMemberNotificationSignUseCase {
    NotificationSignDTO getMemberNotificationSign(String memberId);
}

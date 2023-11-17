package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.notification.application.dto.NotificationSignDTO;
import refrigerator.back.notification.application.port.in.memberNotification.CreateMemberNotificationUseCase;
import refrigerator.back.notification.application.port.in.memberNotification.FindMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.in.memberNotification.TurnOffMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.out.memberNotification.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.memberNotification.CreateMemberNotificationPort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;

@Service
@RequiredArgsConstructor
public class MemberNotificationService implements
        CreateMemberNotificationUseCase, FindMemberNotificationSignUseCase, TurnOffMemberNotificationSignUseCase {

    private final CreateMemberNotificationPort createMemberNotificationPort;
    private final FindMemberNotificationSignPort findMemberNotificationSignPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    @Override
    public NotificationSignDTO getMemberNotificationSign(String memberId) {
        return new NotificationSignDTO(
                findMemberNotificationSignPort.getSign(memberId)
                        .orElseGet(() -> {
                            createMemberNotification(memberId);
                            return false;
                        }));
    }

    @Override
    public void createMemberNotification(String memberId) {
        createMemberNotificationPort.create(memberId);
    }

    @Override
    public void turnOffMemberNotification(String memberId) {
        modifyMemberNotificationPort.modify(memberId, false);
    }
}

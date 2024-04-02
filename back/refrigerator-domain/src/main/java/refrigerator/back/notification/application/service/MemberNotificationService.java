package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.dto.NotificationSignDTO;
import refrigerator.back.notification.application.port.in.CreateMemberNotificationUseCase;
import refrigerator.back.notification.application.port.in.FindMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.in.TurnOffMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberNotificationService implements
        CreateMemberNotificationUseCase, FindMemberNotificationSignUseCase, TurnOffMemberNotificationSignUseCase {

    private final SaveMemberNotificationPort saveMemberNotificationPort;
    private final FindMemberNotificationPort findMemberNotificationPort;

    @Override
    public NotificationSignDTO getMemberNotificationSign(String memberId) {
        return new NotificationSignDTO(findMemberNotificationPort.getMemberNotification(memberId).getSign());
    }

    @Override
    public void createMemberNotification(String memberId) {

        MemberNotification memberNotification = MemberNotification.builder()
                .email(memberId)
                .sign(false)
                .build();

        saveMemberNotificationPort.save(memberNotification);
    }

    @Override
    public void turnOffMemberNotification(String memberId) {

        MemberNotification memberNotification = findMemberNotificationPort.getMemberNotification(memberId);
        memberNotification.setSign(false);

        saveMemberNotificationPort.save(memberNotification);
    }

}

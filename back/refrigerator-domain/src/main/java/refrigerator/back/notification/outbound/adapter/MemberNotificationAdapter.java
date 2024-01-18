package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;
import refrigerator.back.notification.exception.NotificationExceptionType;


@Component
@RequiredArgsConstructor
public class MemberNotificationAdapter implements SaveMemberNotificationPort, FindMemberNotificationPort {

    private final MemberNotificationPersistenceRepository repository;

    @Override
    public String save(MemberNotification memberNotification) {
        return repository.save(memberNotification).getId();
    }


    @Override
    public MemberNotification getMemberNotification(String memberId) {
        return repository.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.TEST_ERROR));
    }
}
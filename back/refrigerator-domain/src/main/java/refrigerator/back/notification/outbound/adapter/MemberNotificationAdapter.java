package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.port.out.memberNotification.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.memberNotification.CreateMemberNotificationPort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;
import refrigerator.back.notification.exception.NotificationExceptionType;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class MemberNotificationAdapter implements CreateMemberNotificationPort, ModifyMemberNotificationPort, FindMemberNotificationSignPort {

    private final MemberNotificationPersistenceRepository repository;

    @Override
    public String create(String memberId) {

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(memberId)
                .sign(false)
                .build();

        return repository.save(memberNotification).getId();
    }

    @Override
    public String modify(String memberId, boolean value) {
        MemberNotification memberNotification = repository.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.TEST_ERROR));

        memberNotification.setSign(value);
        return repository.save(memberNotification).getId();
    }

    @Override
    public Optional<Boolean> getSign(String memberId) {

        MemberNotification memberNotification = repository.findByMemberId(memberId).orElse(null);

        if(memberNotification == null)
            return Optional.empty();

        return Optional.ofNullable(memberNotification.getSign());
    }
}
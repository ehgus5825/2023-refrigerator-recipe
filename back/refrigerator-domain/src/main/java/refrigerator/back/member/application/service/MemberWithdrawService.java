package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.WithdrawUseCase;
import refrigerator.back.member.application.port.out.UpdateMemberPort;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberWithdrawService implements WithdrawUseCase {

    private final UpdateMemberPort updateMemberPort;

    @Override
    public void withdrawMember(String email) {
        updateMemberPort.updateToStatus(email, MemberStatus.LEAVE_STATUS);
    }
}

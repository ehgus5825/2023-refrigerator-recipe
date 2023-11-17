package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.CheckEmailUseCase;
import refrigerator.back.member.application.port.out.FindMemberCountPort;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberEmailCheckService implements CheckEmailUseCase {

    private final FindMemberCountPort findMemberCountPort;

    @Override
    public void isDuplicated(String email) {
        Integer number = findMemberCountPort.countByEmail(email);
        Member.isDuplicatedByEmail(number);
    }

}

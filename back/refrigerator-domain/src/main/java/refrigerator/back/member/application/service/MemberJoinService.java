package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.common.RandomNumber;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.SaveMemberPort;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberJoinService implements JoinUseCase {

    private final SaveMemberPort saveMemberPort;
    private final CurrentTime<LocalDateTime> currentTime;
    private final RandomNumber<Integer> randomNumber;
    
    @Override
    public Long join(String email, String password, String nickname) {
        Member member = Member.join(
                email,
                password,
                nickname,
                randomNumber.getNumber(),
                currentTime.now());
        return saveMemberPort.createMember(member);
    }

}

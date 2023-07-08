package refrigerator.back.member.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.FindMemberCountPort;
import refrigerator.back.member.outbound.repository.jpa.MemberRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberLookUpAdapter implements FindMemberPort, FindMemberCountPort {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Integer countByEmail(String email) {
        return memberRepository.countByEmail(email).intValue();
    }
}

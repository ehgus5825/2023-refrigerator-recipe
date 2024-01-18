package refrigerator.back.member.application.port.out;


import refrigerator.back.member.application.domain.entity.Member;

public interface SaveMemberPort {
    Long createMember(Member member);
}

package refrigerator.back.member.application.port.out;

public interface FindMemberCountPort {
    Integer countByEmail(String email);
}

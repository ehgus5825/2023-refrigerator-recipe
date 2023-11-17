package refrigerator.back.member.application.port.in;

public interface ModifyMemberUseCase {

    void modifyNickname(String email, String nickname);
    void modifyPassword(String email, String password);
    void modifyProfileImage(String email, Integer imageNo);

}

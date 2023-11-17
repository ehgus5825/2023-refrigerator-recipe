package refrigerator.back.member.application.port.in;


public interface JoinUseCase {
    Long join(String email, String password, String nickname);

}

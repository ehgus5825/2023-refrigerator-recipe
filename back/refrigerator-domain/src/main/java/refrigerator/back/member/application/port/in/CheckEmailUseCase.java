package refrigerator.back.member.application.port.in;

public interface CheckEmailUseCase {
    void isDuplicated(String email);
}

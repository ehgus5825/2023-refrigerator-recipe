package refrigerator.back.authentication.application.port.in;

/**
 * Security Context 에서 인증된 사용자의 정보를 가져오는 UseCase
 */
public interface GetMemberEmailUseCase {

    String getMemberEmail();

}

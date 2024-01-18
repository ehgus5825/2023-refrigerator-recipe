package refrigerator.server.security.common.email;

/**
 * Security Context 에서 인증된 사용자의 정보를 가져오는 UseCase
 */
public interface GetMemberEmailUseCase {

    String getMemberEmail();

}

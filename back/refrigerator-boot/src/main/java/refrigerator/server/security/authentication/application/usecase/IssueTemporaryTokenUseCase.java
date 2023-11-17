package refrigerator.server.security.authentication.application.usecase;

public interface IssueTemporaryTokenUseCase {
    String issueTemporaryAccessToken(String email);
}

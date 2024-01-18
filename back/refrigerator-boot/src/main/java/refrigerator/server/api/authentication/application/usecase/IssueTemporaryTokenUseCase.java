package refrigerator.server.api.authentication.application.usecase;

public interface IssueTemporaryTokenUseCase {
    String issueTemporaryAccessToken(String email);
}

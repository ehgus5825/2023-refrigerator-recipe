package refrigerator.server.security.authentication.application.usecase;

public interface RestrictAccessUseCase {
    Boolean restrictAccessToTokens(String refreshToken);
}

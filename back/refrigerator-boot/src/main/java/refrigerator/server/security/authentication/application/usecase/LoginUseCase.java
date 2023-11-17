package refrigerator.server.security.authentication.application.usecase;

import refrigerator.server.security.authentication.application.TokenDto;

public interface LoginUseCase {
    TokenDto login(String email, String password);
}

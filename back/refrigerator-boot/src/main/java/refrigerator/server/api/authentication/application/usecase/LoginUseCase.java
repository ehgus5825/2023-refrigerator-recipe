package refrigerator.server.api.authentication.application.usecase;

import refrigerator.server.api.authentication.application.dto.TokenDto;

public interface LoginUseCase {
    TokenDto login(String email, String password);
}

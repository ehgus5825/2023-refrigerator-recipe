package refrigerator.server.security.authentication.application.usecase;

import refrigerator.server.security.authentication.application.TokenDto;

public interface ReissueUseCase {
    TokenDto reissue(String refreshToken);
}

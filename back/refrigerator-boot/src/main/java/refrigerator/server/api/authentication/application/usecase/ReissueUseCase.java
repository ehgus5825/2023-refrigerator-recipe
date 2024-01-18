package refrigerator.server.api.authentication.application.usecase;

import refrigerator.server.api.authentication.application.dto.TokenDto;

public interface ReissueUseCase {
    TokenDto reissue(String refreshToken);
}

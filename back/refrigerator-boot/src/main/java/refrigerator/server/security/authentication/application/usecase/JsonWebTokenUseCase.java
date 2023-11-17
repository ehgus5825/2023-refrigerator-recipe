package refrigerator.server.security.authentication.application.usecase;

import refrigerator.server.security.authentication.application.TokenStatus;
import refrigerator.server.security.authentication.application.ClaimsDto;

public interface JsonWebTokenUseCase {
    String createToken(String username, String authority, long expirationTime);
    ClaimsDto parseClaims(String token);
    TokenStatus getTokenStatus(String token);
}

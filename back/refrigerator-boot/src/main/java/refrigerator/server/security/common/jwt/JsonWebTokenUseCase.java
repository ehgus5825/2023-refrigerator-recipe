package refrigerator.server.security.common.jwt;

import refrigerator.server.security.common.dto.ClaimsDto;

public interface JsonWebTokenUseCase {
    String createToken(String username, String authority, long expirationTime);
    ClaimsDto parseClaims(String token);
    TokenStatus getTokenStatus(String token);
}

package refrigerator.server.security.common;

import refrigerator.back.authentication.application.domain.RefreshToken;
import refrigerator.back.authentication.application.port.out.RefreshTokenPort;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.server.security.common.jwt.JsonWebTokenUseCase;

import static refrigerator.server.security.common.jwt.JsonWebTokenKey.REFRESH_TOKEN_EXPIRE_TIME;

public class TestTokenService {
    public static String getToken(JsonWebTokenUseCase jsonWebTokenUseCase){
        return "Bearer " + jsonWebTokenUseCase.
                createToken("mstest102@gmail.com", MemberStatus.STEADY_STATUS.toString(), 5000);
    }

    public static String getRefreshToken(JsonWebTokenUseCase jsonWebTokenUseCase, RefreshTokenPort refreshTokenPort, boolean tag){
        String token = jsonWebTokenUseCase.createToken("mstest102@gmail.com", MemberStatus.STEADY_STATUS.toString(), REFRESH_TOKEN_EXPIRE_TIME);
        refreshTokenPort.save(new RefreshToken("mstest102@gmail.com", token, tag));
        return token;
    }
}

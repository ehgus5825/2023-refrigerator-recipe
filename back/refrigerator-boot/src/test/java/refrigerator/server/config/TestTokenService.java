package refrigerator.server.config;

import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

public class TestTokenService {
    public static String getToken(JsonWebTokenUseCase jsonWebTokenUseCase){
        return "Bearer " + jsonWebTokenUseCase.createToken("mstest102@gmail.com", MemberStatus.STEADY_STATUS.toString(), 5000);
    }
}

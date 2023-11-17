package refrigerator.server.security.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.server.security.authentication.application.usecase.IssueTemporaryTokenUseCase;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;


@Service
@RequiredArgsConstructor
public class IssueTemporaryTokenService implements IssueTemporaryTokenUseCase {

    private final GetMemberCredentialsPort getMemberCredentialsUseCase;
    private final JsonWebTokenUseCase jsonWebTokenUseCase;

    @Override
    public String issueTemporaryAccessToken(String email) {
        UserDto user = getMemberCredentialsUseCase.getUser(email);
        return jsonWebTokenUseCase.createToken(email, user.getStatus(), 1000 * 60 * 10);
    }
}

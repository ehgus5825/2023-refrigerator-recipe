package refrigerator.server.api.authentication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.security.authentication.application.TokenDto;
import refrigerator.server.security.authentication.application.usecase.IssueTemporaryTokenUseCase;
import refrigerator.server.security.authentication.application.usecase.ReissueUseCase;
import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.authentication.dto.TemporaryAccessTokenRequestDTO;
import refrigerator.server.api.authentication.dto.TemporaryAccessTokenResponseDTO;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;


@RestController
@Slf4j
public class TokenIssueController {

    private final ReissueUseCase tokenReissueUseCase;
    private final IssueTemporaryTokenUseCase issueTemporaryAccessToken;
    private final String grantType;

    public TokenIssueController(ReissueUseCase tokenReissueUseCase,
                                IssueTemporaryTokenUseCase issueTemporaryAccessToken,
                                @Value("${jwt.type}") String grantType) {
        this.tokenReissueUseCase = tokenReissueUseCase;
        this.issueTemporaryAccessToken = issueTemporaryAccessToken;
        this.grantType = grantType;
    }

    @PostMapping("/api/auth/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto reissue(HttpServletRequest request){
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        TokenDto token = tokenReissueUseCase.reissue(refreshToken);
        token.removeRefreshToken();
        return token;
    }

    @PostMapping("/api/auth/issue/temporary-token")
    public TemporaryAccessTokenResponseDTO issueTemporaryToken(
            @RequestBody @Valid TemporaryAccessTokenRequestDTO request,
            BindingResult result){
        check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        String authToken = issueTemporaryAccessToken.issueTemporaryAccessToken(request.getEmail());
        return new TemporaryAccessTokenResponseDTO(grantType, authToken);
    }

}

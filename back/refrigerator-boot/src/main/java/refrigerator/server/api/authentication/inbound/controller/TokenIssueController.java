package refrigerator.server.api.authentication.inbound.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.authentication.application.usecase.IssueTemporaryTokenUseCase;
import refrigerator.server.api.authentication.application.usecase.ReissueUseCase;
import refrigerator.server.api.authentication.application.dto.TokenDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.authentication.inbound.dto.request.TemporaryAccessTokenRequestDTO;
import refrigerator.server.api.authentication.inbound.dto.response.TemporaryAccessTokenResponseDTO;
import refrigerator.server.api.authentication.inbound.cookie.RefreshTokenCookie;

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

    // RefreshToken이 있다면 accessToken을 다시 반환해주는 건가? 그런듯
    // 쿠키에서 refreshToken 조회
    // refreshToken로 redis의 RefreshToken 조회
    // refreshToken이 유효한지 확인
    // 유효하다면 refreshToken 권한 체크 및 페이로드 정보 뽑기 (안 유효하다면 예외발생)
    // 그 페이로드 정보로 AccessToken을 만듬
    // 그리고 반환.

    @PostMapping("/api/auth/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto reissue(HttpServletRequest request){

        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        TokenDto token = tokenReissueUseCase.reissue(refreshToken);
        token.removeRefreshToken();
        return token;
    }

    // 멤버 조회
    // 그 멤버 정보로 토큰 생성 (AccessToken O)
    // grantType과 함께 반환
    // TODO : 없어져야하는 거 아닌가? 이메일 하나로 토큰을 받을 수 있다는 건 위험한데
    @PostMapping("/api/auth/issue/temporary-token")
    public TemporaryAccessTokenResponseDTO issueTemporaryToken(
            @Valid @RequestBody TemporaryAccessTokenRequestDTO request,
            BindingResult result){

        check(result, MemberExceptionType.INCORRECT_EMAIL_FORMAT);

        String authToken = issueTemporaryAccessToken.issueTemporaryAccessToken(request.getEmail());
        return new TemporaryAccessTokenResponseDTO(grantType, authToken);
    }
}

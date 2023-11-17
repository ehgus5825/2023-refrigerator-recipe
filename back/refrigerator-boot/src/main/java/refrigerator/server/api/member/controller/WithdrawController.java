package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.member.application.port.in.WithdrawUseCase;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;
import refrigerator.server.security.authentication.application.usecase.RestrictAccessUseCase;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class WithdrawController {

    private final WithdrawUseCase withdrawMemberUseCase;
    private final RestrictAccessUseCase restrictAccessUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @DeleteMapping("/api/members/withdraw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setWithdrawMemberUseCase(HttpServletRequest request,
                                         HttpServletResponse response){
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        withdrawMemberUseCase.withdrawMember(memberInformation.getMemberEmail());
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        restrictAccessUseCase.restrictAccessToTokens(refreshToken);
        response.addCookie(refreshTokenCookie.delete());
    }
}

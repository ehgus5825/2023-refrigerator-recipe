package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.member.application.port.in.WithdrawUseCase;
import refrigerator.server.api.authentication.application.usecase.RestrictAccessUseCase;

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
    public void setWithdrawMember(HttpServletRequest request,
                                  HttpServletResponse response){

        withdrawMemberUseCase.withdrawMember(memberInformation.getMemberEmail());

        response.addCookie(restrictAccessUseCase.restrictAccessToTokens(request.getCookies()));
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
}

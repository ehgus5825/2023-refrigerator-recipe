package refrigerator.server.api.member.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;


import javax.servlet.http.HttpServletResponse;


@RestController
public class WithdrawMemberController {

    private final WithdrawMemberUseCase withdrawMemberUseCase;
    private final CustomCookie refreshTokenCookie;
    private final GetMemberEmailUseCase memberInformation;

    public WithdrawMemberController(WithdrawMemberUseCase withdrawMemberUseCase,
                                    GetMemberEmailUseCase memberInformation) {
        this.withdrawMemberUseCase = withdrawMemberUseCase;
        this.refreshTokenCookie = new RefreshTokenCookie();
        this.memberInformation = memberInformation;
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(HttpServletResponse response){
        withdrawMemberUseCase.withdrawMember(memberInformation.getMemberEmail());
        refreshTokenCookie.delete(response);
    }
}
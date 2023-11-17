package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.member.application.port.in.ModifyMemberUseCase;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;
import refrigerator.server.api.member.dto.request.NicknameModifyRequestDto;
import refrigerator.server.api.member.dto.request.PasswordModifyRequestDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.security.authentication.application.usecase.RestrictAccessUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class ModifyMemberController {

    private final ModifyMemberUseCase modifyMemberUseCase;
    private final PasswordEncoder passwordEncoder;
    private final RestrictAccessUseCase restrictAccessUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PutMapping("/api/members/nickname/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateNicknameUseCase(@RequestBody @Valid NicknameModifyRequestDto request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        String email = memberInformation.getMemberEmail();
        modifyMemberUseCase.modifyNickname(email, request.getNickname());
    }

    @PutMapping("/api/members/profileImage/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateProfileUseCase(@RequestParam("imageNo") Integer imageNo){
        String email = memberInformation.getMemberEmail();
        modifyMemberUseCase.modifyProfileImage(email, imageNo);
    }

    @PutMapping("/api/members/password/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid PasswordModifyRequestDto requestDto,
                               HttpServletRequest request,
                               BindingResult result,
                               HttpServletResponse response){
        RefreshTokenCookie refreshTokenCookie = new RefreshTokenCookie();
        check(result, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        String email = memberInformation.getMemberEmail();
        modifyMemberUseCase.modifyPassword(email, passwordEncoder.encode(requestDto.getPassword()));
        // TODO : 비밀번호 변경, 회원 탈퇴, 로그아웃 등 인증 정보가 제거될 때 아래와 같은 로직이 반복됨 -> 처리할 것
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        restrictAccessUseCase.restrictAccessToTokens(refreshToken);
        response.addCookie(refreshTokenCookie.delete());
    }


}

package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.member.application.port.in.ModifyMemberUseCase;
import refrigerator.server.api.member.dto.request.NicknameModifyRequestDto;
import refrigerator.server.api.member.dto.request.PasswordModifyRequestDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.authentication.application.usecase.RestrictAccessUseCase;

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
    public void setUpdateNickname(@Valid @RequestBody NicknameModifyRequestDto request,
                                  BindingResult result){

        check(result, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);

        String email = memberInformation.getMemberEmail();
        modifyMemberUseCase.modifyNickname(email, request.getNickname());
    }

    @PutMapping("/api/members/profileImage/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void setUpdateProfile(@RequestParam("imageNo") Integer imageNo){

        positiveOrZeroCheck(imageNo);

        String email = memberInformation.getMemberEmail();
        modifyMemberUseCase.modifyProfileImage(email, imageNo);
    }

    @PutMapping("/api/members/password/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@Valid @RequestBody PasswordModifyRequestDto requestDto,
                               BindingResult result,
                               HttpServletRequest request,
                               HttpServletResponse response){

        check(result, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);

        String email = memberInformation.getMemberEmail();
        modifyMemberUseCase.modifyPassword(email, passwordEncoder.encode(requestDto.getPassword()));

        response.addCookie(restrictAccessUseCase.restrictAccessToTokens(request.getCookies()));
    }


}

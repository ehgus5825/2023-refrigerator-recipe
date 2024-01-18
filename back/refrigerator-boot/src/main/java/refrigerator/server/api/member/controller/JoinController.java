package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.CheckEmailUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.notification.application.port.in.CreateMemberNotificationUseCase;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.EmailCheckRequestDto;
import refrigerator.server.api.member.dto.request.JoinRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static refrigerator.back.member.exception.MemberExceptionType.*;
import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;

/**
 * 회원 가입 : 이메일 중복 확인 -> 회원 가입
 */
@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinUseCase joinUseCase;
    private final CheckEmailUseCase duplicateCheckEmailUseCase;
    private final PasswordEncoder passwordEncoder;
    private final MemberEmailCheckCookieAdapter cookieAdapter;

    private final CreateMemberNotificationUseCase createMemberNotificationUseCase;

    @PostMapping("/api/members/email/check")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkEmail(@Valid @RequestBody EmailCheckRequestDto emailCheckRequestDto,
                           BindingResult bindingResult,
                           HttpServletResponse response){

        check(bindingResult, INCORRECT_EMAIL_FORMAT);

        duplicateCheckEmailUseCase.isDuplicated(emailCheckRequestDto.getEmail());
        response.addCookie(cookieAdapter.create(emailCheckRequestDto.getEmail()));
    }

    @PostMapping("/api/members/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinMember(@Valid @RequestBody JoinRequestDto requestDto,
                           BindingResult bindingResult,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse){

        multiCheck(bindingResult);

        if (!cookieAdapter.isExist(httpServletRequest.getCookies(), requestDto.getEmail())){
            throw new BusinessException(NOT_COMPLETED_EMAIL_DUPLICATION_CHECK);
        }

        joinUseCase.join(
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname());

        createMemberNotificationUseCase.createMemberNotification(requestDto.getEmail());

        httpServletResponse.addCookie(cookieAdapter.delete());
    }
}

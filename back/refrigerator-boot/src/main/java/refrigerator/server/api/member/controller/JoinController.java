package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.CheckEmailUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.EmailCheckRequestDto;
import refrigerator.server.api.member.dto.request.JoinRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static refrigerator.server.api.global.common.InputDataFormatCheck.EMAIL_REGEX;
import static refrigerator.server.api.global.common.InputDataFormatCheck.inputCheck;

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

    @PostMapping("/api/members/email/check")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkEmail(@RequestBody EmailCheckRequestDto request,
                                    HttpServletResponse response){
        inputCheck(
                EMAIL_REGEX,
                request.getEmail(),
                MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        duplicateCheckEmailUseCase.isDuplicated(request.getEmail());
        response.addCookie(cookieAdapter.create(request.getEmail()));
    }

    @PostMapping("/api/members/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinMember(
            @RequestBody JoinRequestDto requestDto,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse){
        if (!cookieAdapter.isExist(httpServletRequest.getCookies(), requestDto.getEmail())){
            throw new BusinessException(MemberExceptionType.NOT_COMPLETED_EMAIL_DUPLICATION_CHECK);
        }
        requestDto.check();
        joinUseCase.join(
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname());
        httpServletResponse.addCookie(cookieAdapter.delete());
    }


}

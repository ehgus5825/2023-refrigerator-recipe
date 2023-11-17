package refrigerator.back.member.exception;

import lombok.AllArgsConstructor;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
public enum MemberExceptionType implements BasicExceptionType {
    INCORRECT_EMAIL_FORMAT("INCORRECT_EMAIL_FORMAT", "이메일 형식에 어긋납니다.", BAD_REQUEST),
    EMPTY_INPUT_DATA("EMPTY_INPUT_DATA", "입력값이 없습니다.", BAD_REQUEST),
    INCORRECT_PASSWORD_FORMAT("INCORRECT_PASSWORD_FORMAT", "비밀번호 형식에 어긋납니다.", BAD_REQUEST),
    INCORRECT_NICKNAME_FORMAT("INCORRECT_NICKNAME_FORMAT", "허용하지 않는 닉네임 형식입니다.", BAD_REQUEST),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "중복된 이메일 입니다.", BAD_REQUEST),
    NOT_FOUND_MEMBER("NOT_FOUND_MEMBER", "회원을 찾을 수 없습니다.", NOT_FOUND),
    BLOCKED_MEMBER("BLOCKED_MEMBER", "차단된 회원입니다.", NOT_FOUND),
    DORMANT_MEMBER("DORMANT_MEMBER", "휴면 계정으로 전환된 회원입니다.", NOT_FOUND),
    NOT_FOUND_MEMBER_ROLE("NOT_FOUND_MEMBER_ROLE", "해당 접근 권한이 존재하지 않습니다.", FORBIDDEN),
    NOT_FOUND_MEMBER_STATUS("NOT_FOUND_MEMBER_STATUS", "해당 회원 상태가 없습니다.", NOT_FOUND),
    NOT_FOUND_PROFILE_IMAGE("NOT_FOUND_PROFILE_IMAGE", "해당 이미지를 찾을 수 없습니다.", NOT_FOUND),
    EQUAL_OLD_PASSWORD("EQUAL_OLD_PASSWORD", "입력하신 비밀번호가 기존 비밀번호와 동일합니다.", BAD_REQUEST),
    NOT_COMPLETED_IDENTIFICATION("NOT_COMPLETED_IDENTIFICATION", "본인 인증을 하지 않은 사용자 입니다.", BAD_REQUEST),
    NOT_COMPLETED_EMAIL_DUPLICATION_CHECK("NOT_COMPLETED_EMAIL_DUPLICATION_CHECK", "이메일 중복 확인이 진행되지 않았습니다.", BAD_REQUEST),
    WITHDRAWN_MEMBER("WITHDRAWN_MEMBER", "탈퇴한 회원 입니다.", BAD_REQUEST),
    FAIL_UPDATE_MEMBER_NICKNAME("FAIL_UPDATE_MEMBER_NICKNAME", "회원 닉네임을 수정하지 못했습니다.", BAD_REQUEST),
    FAIL_UPDATE_MEMBER_PASSWORD("FAIL_UPDATE_MEMBER_PASSWORD", "회원 비밀번호를 수정하지 못했습니다.", BAD_REQUEST),
    FAIL_UPDATE_MEMBER_PROFILE("FAIL_UPDATE_MEMBER_PROFILE", "회원 프로필을 수정하지 못했습니다.", BAD_REQUEST),
    FAIL_UPDATE_MEMBER_STATUS("FAIL_UPDATE_MEMBER_STATUS", "회원 상태를 수정하지 못했습니다.", BAD_REQUEST)
    ;

    private String errorCode;
    private String message;
    private BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {
        return httpStatus;
    }
}

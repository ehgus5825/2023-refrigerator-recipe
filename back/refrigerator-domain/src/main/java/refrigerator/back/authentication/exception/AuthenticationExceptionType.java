package refrigerator.back.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
@Getter
public enum AuthenticationExceptionType implements BasicExceptionType {

    NOT_FOUND_AUTHORITY("NOT_FOUND_AUTHORITY", "권한이 존재하지 않습니다.", BAD_REQUEST),
    NOT_EQUAL_PASSWORD("NOT_EQUAL_PASSWORD", "비밀번호가 일치하지 않습니다.", BAD_REQUEST),
    ALREADY_LOGIN_MEMBER("WRONG_REQUEST", "잘못된 요청입니다.", BAD_REQUEST),
    ALREADY_LOGOUT_MEMBER("ALREADY_LOGOUT_MEMBER", "이미 로그아웃된 사용자 입니다.", BAD_REQUEST),
    FAIL_ACCESS_BY_TOKEN("FAIL_ACCESS_BY_TOKEN", "접근이 불가능한 사용자입니다.", BAD_REQUEST),
    NOT_INIT_PROFILE_AND_NICKNAME("NOT_INIT_PROFILE_AND_NICKNAME", "프로필과 닉네임이 초기화되지 않았습니다.", BAD_REQUEST),
    NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "토큰을 찾을 수 없습니다.", BAD_REQUEST),
    NOT_FOUND_COOKIE("NOT_FOUND_COOKIE", "쿠키가 존재하지 않습니다.", BAD_REQUEST),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "기간이 만료된 토큰입니다.", BAD_REQUEST),
    FAIL_REDIRECT("FAIL_REDIRECT", "리다이렉트 경로가 잘못되었습니다.", BAD_REQUEST)
    ;

    private final String errorCode;
    private final String errorMessage;
    private final BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {
        return httpStatus;
    }
}

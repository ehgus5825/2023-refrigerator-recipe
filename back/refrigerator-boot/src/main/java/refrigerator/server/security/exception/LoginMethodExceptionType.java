package refrigerator.server.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

@Getter
@AllArgsConstructor
public enum LoginMethodExceptionType implements BasicExceptionType{

    EMPTY_NAVER_LOGIN_ATTRIBUTES("EMPTY_NAVER_LOGIN_ATTRIBUTES", "네이버 간편 로그인 속성값이 비어있습니다.", BasicHttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final BasicHttpStatus httpStatus;

}

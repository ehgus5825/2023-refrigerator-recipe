package refrigerator.server.security.exception;

import refrigerator.back.global.exception.BasicException;
import refrigerator.back.global.exception.BasicExceptionType;

public class CustomAuthenticationException extends BasicException {

    public CustomAuthenticationException(BasicExceptionType basicExceptionType) {
        super(basicExceptionType);
    }

    public CustomAuthenticationException(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause, basicExceptionType);
    }
}

package refrigerator.server.security.common.exception;

import refrigerator.back.global.exception.BasicException;
import refrigerator.back.global.exception.BasicExceptionType;

public class JsonWebTokenException extends BasicException {

    public JsonWebTokenException(BasicExceptionType basicExceptionType) {
        super(basicExceptionType);
    }

    public JsonWebTokenException(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause, basicExceptionType);
    }
}

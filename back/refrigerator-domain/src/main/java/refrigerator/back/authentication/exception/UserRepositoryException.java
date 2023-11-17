package refrigerator.back.authentication.exception;

import refrigerator.back.global.exception.BasicException;
import refrigerator.back.global.exception.BasicExceptionType;

public class UserRepositoryException extends BasicException {

    public UserRepositoryException(BasicExceptionType basicExceptionType) {
        super(basicExceptionType);
    }

    public UserRepositoryException(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause, basicExceptionType);
    }
}

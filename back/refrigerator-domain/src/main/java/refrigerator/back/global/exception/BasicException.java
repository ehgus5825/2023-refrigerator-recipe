package refrigerator.back.global.exception;

import lombok.Getter;

@Getter
public class BasicException extends RuntimeException{

    private final BasicExceptionType basicExceptionType;

    public BasicException(BasicExceptionType basicExceptionType) {
        super(basicExceptionType.getMessage());
        this.basicExceptionType = basicExceptionType;
    }

    public BasicException(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause);
        this.basicExceptionType = basicExceptionType;
    }
}

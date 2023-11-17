package refrigerator.back.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final BasicExceptionType basicExceptionType;

    public BusinessException(BasicExceptionType basicExceptionType) {
        super(basicExceptionType.getMessage());
        this.basicExceptionType = basicExceptionType;
    }

    public BusinessException(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause);
        this.basicExceptionType = basicExceptionType;
    }
}

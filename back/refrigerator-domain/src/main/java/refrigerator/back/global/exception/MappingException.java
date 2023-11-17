package refrigerator.back.global.exception;

public class MappingException extends BasicException{
    public MappingException(BasicExceptionType basicExceptionType) {
        super(basicExceptionType);
    }

    public MappingException(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause, basicExceptionType);
    }
}

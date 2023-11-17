package refrigerator.back.global.exception;


public class S3Exception extends BasicException{

    public S3Exception(BasicExceptionType basicExceptionType) {
        super(basicExceptionType);
    }

    public S3Exception(Throwable cause, BasicExceptionType basicExceptionType) {
        super(cause, basicExceptionType);
    }

}

package refrigerator.back.global.exception;


public interface BasicExceptionType {
    String getErrorCode();
    String getMessage();
    BasicHttpStatus getHttpStatus();
}

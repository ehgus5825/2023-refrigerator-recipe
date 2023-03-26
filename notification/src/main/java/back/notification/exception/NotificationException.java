package back.notification.exception;

import lombok.Getter;

@Getter
public class NotificationException extends RuntimeException{

    private final NotificationExceptionType exceptionType;

    public NotificationException(String message, NotificationExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public NotificationException(String message, Throwable cause, NotificationExceptionType exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }
}

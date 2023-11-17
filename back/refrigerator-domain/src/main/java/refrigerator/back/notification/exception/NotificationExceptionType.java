package refrigerator.back.notification.exception;

import lombok.AllArgsConstructor;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
public enum NotificationExceptionType implements BasicExceptionType {


    TEST_ERROR("TEST_ERROR", "테스트 오류", BAD_REQUEST),
    NOTIFICATION_READ_FAIL("NOTIFICATION_READ_FAIL", "알림 조회 실패.", BAD_REQUEST),
    NOTICE_NOTIFICATION_CREATE_FAIL("NOTICE_NOTIFICATION_CREATE_FAIL", "공지사항 알림 생성 실패.", BAD_REQUEST),
    ADD_INGREDIENT_NOTIFICATION_CREATE_FAIL("ADD_INGREDIENT_NOTIFICATION_CREATE_FAIL", "식재료 추가 알림 생성 실패.", BAD_REQUEST),
    NOTIFICATION_CREATE_FAIL("NOTIFICATION_CREATE_FAIL", "알림 생성 실패.", BAD_REQUEST),
    NOTIFICATION_DELETE_FAIL("NOTIFICATION_DELETE_FAIL", "알림 삭제 실패.", BAD_REQUEST)
    ;

    private String errorCode;
    private String message;
    private BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {return httpStatus;}
}

package refrigerator.back.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;


@Getter
@AllArgsConstructor
public enum CommentExceptionType implements BasicExceptionType {
    NOT_FOUND_COMMENT("NOT_FOUND_COMMENT", "해당 댓글을 찾지 못했습니다.", BAD_REQUEST),
    NO_EDIT_RIGHTS("NO_EDIT_RIGHTS", "수정 권한이 없습니다.", BAD_REQUEST),
    FAIL_DELETE_COMMENT("FAIL_DELETE_COMMENT", "이미 삭제된 댓글이거나 존재하지 않는 댓글입니다.", BAD_REQUEST),
    FAIL_MODIFY_COMMENT("FAIL_MODIFY_COMMENT", "댓글을 수정할 수 없습니다.", BAD_REQUEST),
    DUPLICATE_HEART_REQUEST("DUPLICATE_HEART_REQUEST", "이미 좋아요가 눌러진 상태이거나 취소된 상태입니다.", BAD_REQUEST),
    NOT_VALID_REQUEST_BODY("NOT_VALID_REQUEST_BODY", "입력하신 데이터가 유효하지 않습니다.", BAD_REQUEST),
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
    public BasicHttpStatus getHttpStatus() {
        return httpStatus;
    }
}

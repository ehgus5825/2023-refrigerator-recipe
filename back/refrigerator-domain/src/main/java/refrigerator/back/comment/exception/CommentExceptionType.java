package refrigerator.back.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;


@Getter
@AllArgsConstructor
public enum CommentExceptionType implements BasicExceptionType {
    INPUT_ERROR_SORTCONDITION("INPUT_ERROR_SORTCONDITION", "정렬 기준을 올바르게 입력해주세요.", BAD_REQUEST),
    NOT_FOUND_COMMENT("NOT_FOUND_COMMENT", "해당 댓글을 찾지 못했습니다.", BAD_REQUEST),
    NO_EDIT_RIGHTS("NO_EDIT_RIGHTS", "수정 권한이 없습니다.", BAD_REQUEST),
    FAIL_DELETE_COMMENT("FAIL_DELETE_COMMENT", "이미 삭제된 댓글이거나 존재하지 않는 댓글입니다.", BAD_REQUEST),
    FAIL_MODIFY_COMMENT("FAIL_MODIFY_COMMENT", "댓글을 수정할 수 없습니다.", BAD_REQUEST),
    DUPLICATE_HEART_REQUEST("DUPLICATE_HEART_REQUEST", "이미 좋아요가 눌러진 상태이거나 취소된 상태입니다.", BAD_REQUEST),
    EMPTY_CONTENT("EMPTY_CONTENT", "내용이 비어있습니다.", BAD_REQUEST),
    ;

    private final String errorCode;
    private final String message;
    private final BasicHttpStatus httpStatus;

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

package refrigerator.back.global.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

@Getter
@AllArgsConstructor
public enum S3ExceptionType implements BasicExceptionType {

    DUPLICATE_FILE_NAME("DUPLICATE_FILE_NAME", "여러 폴더에 중복된 파일명이 존재합니다. 확인해주세요.", BasicHttpStatus.BAD_REQUEST)
    ;

    private final String errorCode;
    private final String message;
    private final BasicHttpStatus httpStatus;
}

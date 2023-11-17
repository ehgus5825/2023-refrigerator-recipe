package refrigerator.back.global.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;
import refrigerator.back.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class WriteQueryResultTypeTest {

    @Test
    @DisplayName("쿼리 정상 실행")
    void findTypeByResult() {
        long result = 1;
        WriteQueryResultType type = WriteQueryResultType.findTypeByResult(result);
        assertEquals(WriteQueryResultType.NORMAL, type);
    }

    @Test
    @DisplayName("쿼리 실행 실패 -> 중복된 리소스 존재")
    void findTypeByResultFail1() {
        long result = 3;
        WriteQueryResultType type = WriteQueryResultType.findTypeByResult(result);
        assertEquals(WriteQueryResultType.DUPLICATION, type);
    }

    @Test
    @DisplayName("쿼리 실행 실패 -> 리소스 존재하지 않음")
    void findTypeByResultFail2() {
        long result = 0;
        WriteQueryResultType type = WriteQueryResultType.findTypeByResult(result);
        assertEquals(WriteQueryResultType.FAIL, type);
    }

    @Test
    @DisplayName("리소스 중복을 허용하는 경우 예외 발생")
    void throwExceptionWhenAllowDuplicationResource(){
        WriteQueryResultType fail = WriteQueryResultType.FAIL;
        assertThrows(BusinessException.class, () -> {
            try{
                fail.throwExceptionWhenAllowDuplicationResource(TestExceptionType.ALLOW_DUPLICATION_RESOURCE);
            } catch (BusinessException e){
                assertEquals(TestExceptionType.ALLOW_DUPLICATION_RESOURCE, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("리소스 중복을 허용하는 경우 예외 발생")
    void NotThrowExceptionWhenAllowDuplicationResource(){
        WriteQueryResultType duplication = WriteQueryResultType.DUPLICATION;
        assertDoesNotThrow(() ->
                duplication.throwExceptionWhenAllowDuplicationResource(TestExceptionType.ALLOW_DUPLICATION_RESOURCE));
    }

    @Test
    @DisplayName("리소스 중복을 허용하지 않는 경우 예외 발생")
    void throwExceptionWhenNotAllowDuplicationResource(){
        WriteQueryResultType duplication = WriteQueryResultType.DUPLICATION;
        assertThrows(BusinessException.class, () -> {
            try{
                duplication.throwExceptionWhenNotAllowDuplicationResource(TestExceptionType.NOT_ALLOW_DUPLICATION_RESOURCE);
            } catch (BusinessException e){
                assertEquals(TestExceptionType.NOT_ALLOW_DUPLICATION_RESOURCE, e.getBasicExceptionType());
                throw e;
            }
        });
    }

    @Test
    @DisplayName("리소스 중복을 허용하지 않는 경우 예외 발생")
    void notThrowExceptionWhenNotAllowDuplicationResource(){
        WriteQueryResultType normal = WriteQueryResultType.NORMAL;
        assertDoesNotThrow(() ->
                normal.throwExceptionWhenNotAllowDuplicationResource(TestExceptionType.NOT_ALLOW_DUPLICATION_RESOURCE));
    }

    @Getter
    @AllArgsConstructor
    enum TestExceptionType implements BasicExceptionType{

        ALLOW_DUPLICATION_RESOURCE("ALLOW_DUPLICATION_RESOURCE", "리소스 중복 허용", null),
        NOT_ALLOW_DUPLICATION_RESOURCE("NOT_ALLOW_DUPLICATION_RESOURCE", "리소스 중복 비허용", null)
        ;

        private String errorCode;
        private String message;
        private BasicHttpStatus httpStatus;
    }

}
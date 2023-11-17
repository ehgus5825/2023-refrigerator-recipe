package refrigerator.back.global.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.util.Arrays;

@AllArgsConstructor
@Slf4j
public enum WriteQueryResultType {

    NORMAL(target -> target == 1, "쿼리가 정상적으로 실행되었습니다."),
    DUPLICATION(target -> target > 1, "쿼리는 정상적으로 실행되었으나 여러 리소스에 쿼리가 적용되었습니다."),
    FAIL(target -> target < 1, "대상 리소스가 존재하지 않거나 내부 로직 문제로 쿼리가 정상적으로 진행되지 않았습니다.")
    ;

    private final QueryCondition queryCondition;
    private final String message;

    /**
     * query 결과 값으로 WriteQueryResultType 찾기
     * @param result 쿼리 적용 개수
     * @return WriteQueryResultType
     */
    public static WriteQueryResultType findTypeByResult(long result){
        return Arrays.stream(WriteQueryResultType.values())
                .filter(type -> type.queryCondition.condition(result))
                .findAny().get();
    }

    /**
     * 쿼리가 적용될 리소스의 중복을 허용하는 경우
     * @param exceptionType 예외를 터트릴 타입
     */
    public void throwExceptionWhenAllowDuplicationResource(BasicExceptionType exceptionType){
        if (this == WriteQueryResultType.FAIL){
            log.info("[query error] {}", message);
            throw new BusinessException(exceptionType);
        }
    }

    /**
     * 쿼리가 적용될 리소스의 중복을 허용하지 않는 경우
     * @param exceptionType 예외를 터트릴 타입
     */
    public void throwExceptionWhenNotAllowDuplicationResource(BasicExceptionType exceptionType){
        if (this == WriteQueryResultType.FAIL || this == WriteQueryResultType.DUPLICATION){
            log.info("[query error] {}", message);
            throw new BusinessException(exceptionType);
        }
    }

    private interface QueryCondition{
        boolean condition(long target);
    }

}

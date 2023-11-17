package refrigerator.server.api.global.exception;

import org.springframework.validation.BindingResult;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BusinessException;

public class ValidationExceptionHandler {
    public static void check(BindingResult result, BasicExceptionType exceptionType) {
        if (result.hasErrors()){
            throw new BusinessException(exceptionType);
        }
    }
}

package refrigerator.back.myscore.application.domain;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.exception.MyScoreExceptionType;

public class ScoreScope {

    protected void checkScoreScope(Double score){
        if (score != null && (score <= 0.0 || score > 5.0)) {
            throw new BusinessException(MyScoreExceptionType.WRONG_SCORE_SCOPE);
        }
    }

}

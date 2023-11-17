package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.in.CookingUseCase;
import refrigerator.back.myscore.application.port.out.FindMyScorePort;
import refrigerator.back.myscore.application.port.out.SaveMyScorePort;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MyScoreCookingService implements CookingUseCase {

    private final SaveMyScorePort saveMyScorePort;
    private final FindMyScorePort findMyScorePort;
    private final RecipeScoreModifyHandler handler;
    private final CurrentTime<LocalDateTime> currentTime;

    @Override
    public Long cooking(String memberId, Long recipeId, Double score) {
        try{
            MyScore myScore = findMyScorePort.findByRecipeIdAndMemberId(recipeId, memberId);
            myScore.update(score, handler);
            return myScore.getScoreId();
        } catch(BusinessException e){
            MyScore newScore = MyScore.create(memberId, recipeId, score, currentTime.now(), handler);
            return saveMyScorePort.save(newScore);
        }
    }

}

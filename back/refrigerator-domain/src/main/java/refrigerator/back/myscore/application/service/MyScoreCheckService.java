package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.in.CheckCookingUseCase;
import refrigerator.back.myscore.application.port.out.GetMyScoreNumberPort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyScoreCheckService implements CheckCookingUseCase {

    private final GetMyScoreNumberPort getMyScoreNumberPort;

    @Override
    public Boolean isCooked(Long recipeId, String memberId) {
        Integer number = getMyScoreNumberPort.getByRecipeIdAndMemberId(recipeId, memberId);
        return MyScore.isCooked(number);
    }
}

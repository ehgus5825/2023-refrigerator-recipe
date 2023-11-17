package refrigerator.back.myscore.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.application.port.out.GetMyScoreNumberPort;
import refrigerator.back.myscore.outbound.repository.query.MyScoreSelectQueryRepository;

@Repository
@RequiredArgsConstructor
public class MyScoreCountAdapter implements GetMyScoreNumberPort {

    private final MyScoreSelectQueryRepository queryRepository;

    @Override
    public Integer getByMemberId(String memberId) {
        return queryRepository.selectMyScoreCountByMemberId(memberId).getNumber();
    }

    @Override
    public Integer getByRecipeIdAndMemberId(Long recipeId, String memberId) {
        return queryRepository.selectMyScoreCountByMemberIdAndRecipeId(memberId, recipeId).getNumber();
    }
}

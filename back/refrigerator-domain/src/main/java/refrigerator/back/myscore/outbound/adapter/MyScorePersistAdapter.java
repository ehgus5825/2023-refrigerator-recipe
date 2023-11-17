package refrigerator.back.myscore.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.application.port.out.FindMyScorePort;
import refrigerator.back.myscore.application.port.out.SaveMyScorePort;
import refrigerator.back.myscore.exception.MyScoreExceptionType;
import refrigerator.back.myscore.outbound.repository.jpa.MyScoreJpaRepository;

@Repository
@RequiredArgsConstructor
public class MyScorePersistAdapter implements SaveMyScorePort, FindMyScorePort {

    private final MyScoreJpaRepository jpaRepository;

    @Override
    public Long save(MyScore myScore) {
        return jpaRepository.save(myScore).getScoreId();
    }

    @Override
    public MyScore findByRecipeIdAndMemberId(Long recipeId, String memberId) {
        return jpaRepository.findByRecipeIdAndMemberId(recipeId, memberId).orElseThrow(
                () -> new BusinessException(MyScoreExceptionType.NOT_FOUND_SCORE)
        );
    }
}

package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.domain.MyScore;

public interface FindMyScorePort {
    MyScore findByRecipeIdAndMemberId(Long recipeId, String memberId);
}

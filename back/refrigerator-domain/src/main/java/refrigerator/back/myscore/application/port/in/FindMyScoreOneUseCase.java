package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.dto.MyScoreDto;

public interface FindMyScoreOneUseCase {
    MyScoreDto findMyScoreDto(Long recipeId, String memberId);
}

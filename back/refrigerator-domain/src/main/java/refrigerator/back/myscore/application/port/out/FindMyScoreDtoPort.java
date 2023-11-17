package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.dto.MyScoreDto;

public interface FindMyScoreDtoPort {
    MyScoreDto findMyScoreDto(Long recipeId, String memberId);
}

package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;

import java.util.List;

public interface FindMyScoreListPort {
    List<MyScoreDetailDto> findMyScoreDetails(String memberId, int page, int size);
    InMyScorePreviewsDto findMyScorePreviews(String memberId, int page, int size);
}

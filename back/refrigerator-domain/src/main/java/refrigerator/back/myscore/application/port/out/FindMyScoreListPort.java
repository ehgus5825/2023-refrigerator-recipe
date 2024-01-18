package refrigerator.back.myscore.application.port.out;

import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.dto.MyScorePreviewDto;

import java.util.List;

public interface FindMyScoreListPort {
    List<MyScoreDetailDto> findMyScoreDetails(String memberId, int page, int size);
    List<MyScorePreviewDto> findMyScorePreviews(String memberId, int page, int size);
}

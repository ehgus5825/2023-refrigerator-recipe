package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;

public interface FindMyScorePreviewsUseCase {
    InMyScorePreviewsDto findMyScorePreviews(String memberId, int size);
}

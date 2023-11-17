package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewsUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScoresUseCase;
import refrigerator.back.myscore.application.port.out.FindMyScoreListPort;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyScoreListLookUpService implements FindMyScoresUseCase, FindMyScorePreviewsUseCase {

    private final FindMyScoreListPort findMyScoreListPort;

    @Override
    public List<MyScoreDetailDto> findMyScores(String memberId, int page, int size) {
        return findMyScoreListPort.findMyScoreDetails(memberId, page, size);
    }

    @Override
    public InMyScorePreviewsDto findMyScorePreviews(String memberId, int size) {
        return findMyScoreListPort.findMyScorePreviews(memberId, 0, size);
    }

}

package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.dto.MyScorePreviewDto;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewsUseCase;
import refrigerator.back.myscore.application.port.in.FindMyScoresUseCase;
import refrigerator.back.myscore.application.port.out.FindMyScoreListPort;
import refrigerator.back.myscore.application.port.out.GetMyScoreNumberPort;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyScoreListLookUpService implements FindMyScoresUseCase, FindMyScorePreviewsUseCase {

    private final FindMyScoreListPort findMyScoreListPort;
    private final GetMyScoreNumberPort getMyScoreNumberPort;

    @Override
    public List<MyScoreDetailDto> findMyScores(String memberId, int page, int size) {
        return findMyScoreListPort.findMyScoreDetails(memberId, page, size);
    }

    @Override
    public InMyScorePreviewsDto findMyScorePreviews(String memberId, int size) {
        List<MyScorePreviewDto> scores = findMyScoreListPort.findMyScorePreviews(memberId, 0, size);
        Integer previewSize = getMyScoreNumberPort.getByMemberId(memberId);
        return new InMyScorePreviewsDto(scores, previewSize);
    }

}

package refrigerator.back.myscore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.myscore.application.dto.MyScoreDto;
import refrigerator.back.myscore.application.port.in.FindMyScoreOneUseCase;
import refrigerator.back.myscore.application.port.out.FindMyScoreDtoPort;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyScoreLookUpService implements FindMyScoreOneUseCase {

    private final FindMyScoreDtoPort findMyScoreDtoPort;

    @Override
    public MyScoreDto findMyScoreDto(Long recipeId, String memberId) {
        return findMyScoreDtoPort.findMyScoreDto(recipeId, memberId);
    }

}

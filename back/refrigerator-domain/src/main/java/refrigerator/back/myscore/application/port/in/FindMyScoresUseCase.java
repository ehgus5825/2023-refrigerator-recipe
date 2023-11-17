package refrigerator.back.myscore.application.port.in;

import refrigerator.back.myscore.application.dto.MyScoreDetailDto;

import java.util.List;

public interface FindMyScoresUseCase {
    List<MyScoreDetailDto> findMyScores(String memberId, int page, int size);

}

package refrigerator.back.myscore.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.application.dto.MyScoreDto;
import refrigerator.back.myscore.application.port.out.FindMyScoreDtoPort;
import refrigerator.back.myscore.exception.MyScoreExceptionType;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDto;
import refrigerator.back.myscore.outbound.mapper.OutMyScoreDtoMapper;
import refrigerator.back.myscore.outbound.repository.query.MyScoreSelectQueryRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyScoreLookUpAdapter implements FindMyScoreDtoPort {

    private final MyScoreSelectQueryRepository queryRepository;
    private final OutMyScoreDtoMapper mapper;


    @Override
    public MyScoreDto findMyScoreDto(Long recipeId, String memberId) {
        OutMyScoreDto outMyScoreDto = Optional.ofNullable(queryRepository.selectMyScoreDto(recipeId, memberId))
                .orElseThrow(() -> new BusinessException(MyScoreExceptionType.NOT_FOUND_SCORE));
        return outMyScoreDto.mapping(mapper);
    }

}

package refrigerator.back.myscore.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.dto.MyScorePreviewDto;
import refrigerator.back.myscore.application.port.out.FindMyScoreListPort;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDetailDto;
import refrigerator.back.myscore.outbound.dto.OutMyScorePreviewDto;
import refrigerator.back.myscore.outbound.mapper.OutMyScoreListDtoMapper;
import refrigerator.back.myscore.outbound.repository.query.MyScoreSelectQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MyScoreListLookUpAdapter implements FindMyScoreListPort {

    private final MyScoreSelectQueryRepository queryRepository;
    private final ImageUrlConvert imageUrlConvert;
    private final OutMyScoreListDtoMapper mapper;

    @Override
    public List<MyScoreDetailDto> findMyScoreDetails(String memberId, int page, int size) {
        List<OutMyScoreDetailDto> scores = queryRepository.selectMyScoreDetailDtos(memberId, PageRequest.of(page, size));
        return scores.stream()
                .map(score -> score.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
    }

    @Override
    public InMyScorePreviewsDto findMyScorePreviews(String memberId, int page, int size) {
        List<OutMyScorePreviewDto> scores = queryRepository.selectMyScorePreviewDtos(memberId, PageRequest.of(page, size));
        int previewSize = queryRepository.selectMyScoreCountByMemberId(memberId).getNumber();
        List<MyScorePreviewDto> previews = scores.stream()
                .map(score -> score.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
        return new InMyScorePreviewsDto(previews, previewSize);
    }

}

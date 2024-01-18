package refrigerator.server.api.myscore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.port.in.FindMyScoresUseCase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class MyPageScoreDetailsController {

    private final FindMyScoresUseCase findMyScoresUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/my-page/scores")
    public BasicListResponseDTO<MyScoreDetailDto> findMyScores(
             @RequestParam(value = "size", defaultValue = "11") @Positive Integer size,
             @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page){

        String memberId = getMemberEmailUseCase.getMemberEmail();
        List<MyScoreDetailDto> scores = findMyScoresUseCase.findMyScores(memberId, page, size);
        return new BasicListResponseDTO<>(scores);
    }

}

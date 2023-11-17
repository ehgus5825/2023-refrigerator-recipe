package refrigerator.server.api.myscore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.port.in.FindMyScoresUseCase;
import refrigerator.server.api.myscore.dto.InMyScoreDetailsListDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageScoreDetailsController {

    private final FindMyScoresUseCase findMyScoresUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/my-page/scores")
    public InMyScoreDetailsListDto findMyScores(@RequestParam(value = "size", defaultValue = "11") int size,
                                                @RequestParam("page") int page){
        String memberId = getMemberEmailUseCase.getMemberEmail();
        List<MyScoreDetailDto> scores = findMyScoresUseCase.findMyScores(memberId, page, size);
        return new InMyScoreDetailsListDto(scores);
    }

}

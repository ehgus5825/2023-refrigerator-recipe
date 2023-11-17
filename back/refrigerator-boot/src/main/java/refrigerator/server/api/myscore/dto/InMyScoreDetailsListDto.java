package refrigerator.server.api.myscore.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InMyScoreDetailsListDto {
    List<MyScoreDetailDto> myScores;
}

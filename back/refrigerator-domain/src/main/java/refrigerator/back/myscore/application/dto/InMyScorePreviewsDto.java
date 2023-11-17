package refrigerator.back.myscore.application.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InMyScorePreviewsDto {

    private List<MyScorePreviewDto> myScorePreviews;
    private int myScoreSize;

}

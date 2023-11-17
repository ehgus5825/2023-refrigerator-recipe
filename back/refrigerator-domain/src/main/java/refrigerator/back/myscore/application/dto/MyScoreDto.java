package refrigerator.back.myscore.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class MyScoreDto {

    private Long scoreId;
    private Double score;

}

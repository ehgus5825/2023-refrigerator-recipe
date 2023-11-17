package refrigerator.batch.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@Getter
@ToString
public class OutIngredientGroupDTO {

    private String email;
    private String name;
    private Long count;

    @QueryProjection
    public OutIngredientGroupDTO(String email, String name, Long count) {
        this.email = email;
        this.name = name;
        this.count = count;
    }
}

package refrigerator.back.recipe_searchword.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class OutIngredientDTO {

    private String name;
    private LocalDate date;

    @QueryProjection
    public OutIngredientDTO(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}

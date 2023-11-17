package refrigerator.back.ingredient.application.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDTO {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private String image;

    private String remainDays;

    public void calculateRemainDays(LocalDate now) {
        long between = ChronoUnit.DAYS.between(this.expirationDate, now);

        this.remainDays = (between > 0) ? "+" + between : Long.toString(between);
    }
}

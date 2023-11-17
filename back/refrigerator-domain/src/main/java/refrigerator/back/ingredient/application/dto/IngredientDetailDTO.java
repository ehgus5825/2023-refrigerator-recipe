package refrigerator.back.ingredient.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDetailDTO {

    private Long ingredientID;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    private LocalDate registrationDate;

    private Double volume;

    private String unit;

    private IngredientStorageType storage;

    private String image;

    private String remainDays;

    public void calculateRemainDays(LocalDate now) {
        long between = ChronoUnit.DAYS.between(this.expirationDate, now);

        this.remainDays = (between > 0) ? "+" + between : Long.toString(between);
    }
}
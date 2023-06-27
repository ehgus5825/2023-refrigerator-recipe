package refrigerator.back.ingredient.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDetailDTO extends Image {

    private Long ingredientID;

    private String name;

    private IngredientStorageType storage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    private LocalDate registrationDate;

    private String remainDays;

    private Double volume;

    private String unit;

    private String image;

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
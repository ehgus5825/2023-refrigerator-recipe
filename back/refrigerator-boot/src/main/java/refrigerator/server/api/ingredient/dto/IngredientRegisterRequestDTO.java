package refrigerator.server.api.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRegisterRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;

    @NotNull
    @Positive
    private Double volume;

    @NotNull
    private IngredientStorageType storage;
}
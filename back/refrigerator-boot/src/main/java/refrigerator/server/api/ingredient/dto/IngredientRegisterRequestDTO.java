package refrigerator.server.api.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRegisterRequestDTO {

    @NotBlank
    private String name;

    // TODO : 여기 유효성 검사 수정해야함
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    @FutureOrPresent
    private LocalDate expirationDate;

    @NotNull
    @Positive
    private Double volume;

    @NotNull
    private IngredientStorageType storage;
}
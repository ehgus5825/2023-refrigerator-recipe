package refrigerator.server.api.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientUpdateRequestDTO {

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

package refrigerator.back.ingredient.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSearchCondition {

    IngredientStorageType storage;
    Boolean deadline;
    String email;
}

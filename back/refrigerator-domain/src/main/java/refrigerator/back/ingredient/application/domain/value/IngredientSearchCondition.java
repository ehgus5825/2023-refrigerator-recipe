package refrigerator.back.ingredient.application.domain.value;

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
    
    // true면 유통기한 지난 식재료 조회, false면 유통기한이 남아있는 식재료 조회
    Boolean deadline;

    String email;
}

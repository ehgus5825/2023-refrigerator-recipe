package refrigerator.back.recipe.application.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RecipeDifficulty {

    NO_LEVEL("없음"),
    LEVEL1("초보환영"),
    LEVEL2("보통"),
    LEVEL3("어려움")
    ;

    private String levelName;

    public static RecipeDifficulty lookupByLevelName(String name){
        return Arrays.stream(RecipeDifficulty.values())
                .filter(level -> level.getLevelName().equals(name))
                .findAny()
                .orElseThrow(() -> new BusinessException(RecipeExceptionType.WRONG_DIFFICULTY));
    }

}
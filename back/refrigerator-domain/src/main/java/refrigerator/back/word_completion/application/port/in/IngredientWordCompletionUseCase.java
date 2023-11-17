package refrigerator.back.word_completion.application.port.in;

import java.util.List;

public interface IngredientWordCompletionUseCase {
    List<String> search(String keyword);
}

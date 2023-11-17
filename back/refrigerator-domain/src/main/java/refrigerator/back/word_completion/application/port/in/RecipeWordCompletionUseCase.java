package refrigerator.back.word_completion.application.port.in;

import java.util.List;

public interface RecipeWordCompletionUseCase {
    List<String> search(String keyword);
}

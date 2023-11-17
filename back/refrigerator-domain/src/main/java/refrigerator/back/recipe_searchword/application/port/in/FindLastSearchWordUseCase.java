package refrigerator.back.recipe_searchword.application.port.in;

import java.util.List;

public interface FindLastSearchWordUseCase {
    List<String> getLastSearchWords(String memberId);
}

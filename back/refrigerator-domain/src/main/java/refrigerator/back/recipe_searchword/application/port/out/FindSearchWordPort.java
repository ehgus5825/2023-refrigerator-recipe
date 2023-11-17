package refrigerator.back.recipe_searchword.application.port.out;

import java.util.List;

public interface FindSearchWordPort {
    List<String> findSearchWord(String key);
}

package refrigerator.back.recipe_searchword.application.port.out;

import java.time.LocalDate;
import java.util.List;

public interface FindIngredientsByMemberPort {
    List<String> getIngredients(LocalDate now, String memberId);
}

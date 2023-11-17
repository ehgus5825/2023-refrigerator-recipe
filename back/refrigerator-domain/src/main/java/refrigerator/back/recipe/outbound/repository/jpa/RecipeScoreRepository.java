package refrigerator.back.recipe.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;

public interface RecipeScoreRepository extends JpaRepository<RecipeScore, Long> {
}

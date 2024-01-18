package refrigerator.back.ingredient.outbound.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientPersistenceRepository extends JpaRepository<Ingredient, Long>{

    Optional<Ingredient> findByIdAndDeletedFalse(Long id);

}

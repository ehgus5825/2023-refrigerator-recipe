package refrigerator.back.ingredient.outbound.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientPersistenceRepository extends JpaRepository<Ingredient, Long>{

    List<Ingredient> findByEmailAndDeletedFalse(String email);

    Optional<Ingredient> findByIdAndDeletedFalse(Long id);

}

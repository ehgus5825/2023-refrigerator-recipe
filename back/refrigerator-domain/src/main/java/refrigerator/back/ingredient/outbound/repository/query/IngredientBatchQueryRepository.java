package refrigerator.back.ingredient.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static refrigerator.back.ingredient.application.domain.entity.QIngredient.ingredient;
import static refrigerator.back.ingredient.application.domain.entity.QSuggestedIngredient.suggestedIngredient;


@Component
@RequiredArgsConstructor
public class IngredientBatchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * @param
     */

    public void deleteIngredients() {

        long execute = jpaQueryFactory.delete(ingredient)
                .where(ingredient.deleted.eq(true))
                .execute();

        em.flush();
        em.clear();

    }


    /**
     *
     * @return
     */
    public Long deleteSuggestedIngredient() {

        long execute = jpaQueryFactory.delete(suggestedIngredient)
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    /**
     *
     * @param
     * @return
     */
    public Long saveRegisteredIngredient(RegisteredIngredient ingredient) {
        em.persist(ingredient);

        em.flush();
        em.clear();

        return ingredient.getId();
    }

    public Optional<String> findUnitName(String name) {

        List<String> result = jpaQueryFactory.select(suggestedIngredient.unit)
                .from(suggestedIngredient)
                .where(suggestedIngredient.name.eq(name))
                .groupBy(suggestedIngredient.unit)
                .orderBy(suggestedIngredient.unit.count().desc())
                .limit(1)
                .fetch();

        return Optional.ofNullable(result.get(0));
    }
}

package refrigerator.back.ingredient.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;
import refrigerator.back.ingredient.infra.redis.IngredientCacheKey;
import refrigerator.back.ingredient.outbound.dto.QOutIngredientInRecipeDTO;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


import static refrigerator.back.ingredient.application.domain.entity.QRegisteredIngredient.registeredIngredient;
import static refrigerator.back.ingredient.application.domain.entity.QSuggestedIngredient.suggestedIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;

@Component
@RequiredArgsConstructor
public class SubIngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /** registeredIngredient **/

    /**
     *
     * @param
     * @return
     */
//    @Cacheable(
//            value = IngredientCacheKey.REGISTERED_INGREDIENT,
//            key = "'registered_ingredient'",
//            cacheManager = "registeredIngredientCacheManager"
//    )
    public List<RegisteredIngredient> getRegisteredIngredientList() {
        return jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();
    }

    /** suggestedIngredient **/

    /**
     *
     * @param ingredient
     * @return
     */
    public Long saveSuggestIngredient(SuggestedIngredient ingredient) {
        em.persist(ingredient);
        return ingredient.getId();
    }


    /** recipeIngredient **/

    /**
     *
     * @param
     * @return
     */
    public List<OutIngredientInRecipeDTO> getRecipeIngredient(Long recipeId) {
        return jpaQueryFactory
                .select(new QOutIngredientInRecipeDTO(recipeIngredient.ingredientId, recipeIngredient.name))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeId.eq(recipeId))
                .fetch();
    }

    /**
     *
     * @param
     * @return
     */
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

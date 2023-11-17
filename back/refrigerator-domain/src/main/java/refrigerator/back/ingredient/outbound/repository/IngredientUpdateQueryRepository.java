package refrigerator.back.ingredient.outbound.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import javax.persistence.EntityManager;
import java.util.List;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;

@Component
@RequiredArgsConstructor
public class IngredientUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public Long updateIngredientDeleteStateTrue(Long id) {

        long execute = jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(idCheck(id))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    public Long updateAllIngredientDeleteStateTrue(List<Long> ids) {

        long execute = jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(idListCheck(ids))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    @Transactional
    public Long deleteIngredients() {

        long execute = jpaQueryFactory.delete(ingredient)
                .where(ingredient.deleted.eq(true))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    public BooleanExpression idCheck(Long id) {
        if(id == null) {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }

        return ingredient.id.eq(id);
    }

    public BooleanExpression idListCheck(List<Long> ids) {
        if(ids == null) {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }

        for (Long id : ids) {
            if(id == null) {
                throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
            }
        }

        return ingredient.id.in(ids);
    }
}

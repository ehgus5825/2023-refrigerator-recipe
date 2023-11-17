package refrigerator.back.recipe.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.application.domain.QIngredient;
import refrigerator.back.recipe.outbound.dto.OutMyIngredientDto;
import refrigerator.back.recipe.outbound.dto.QOutMyIngredientDto;
import refrigerator.back.recipe.outbound.mapper.OutMyIngredientDtoMappingCollection;

import java.util.List;

import static refrigerator.back.ingredient.application.domain.QIngredient.*;

@Repository
@RequiredArgsConstructor
public class MyIngredientSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 나의 식재료 목록 조회 쿼리
     * @param memberId 사용자 id
     * @return 나의 식재료 목록
     */
    public OutMyIngredientDtoMappingCollection selectMyIngredientDto(String memberId){
        List<OutMyIngredientDto> result = jpaQueryFactory.select(new QOutMyIngredientDto(
                        ingredient.name,
                        ingredient.capacity,
                        ingredient.capacityUnit))
                .from(ingredient)
                .where(ingredient.email.eq(memberId), ingredient.deleted.eq(false))
                .fetch();
        return new OutMyIngredientDtoMappingCollection(result);
    }
}

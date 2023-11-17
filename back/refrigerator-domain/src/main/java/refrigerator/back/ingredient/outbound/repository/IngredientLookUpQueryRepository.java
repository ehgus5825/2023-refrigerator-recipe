package refrigerator.back.ingredient.outbound.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDTO;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.outbound.dto.QOutIngredientDTO;
import refrigerator.back.ingredient.outbound.dto.QOutIngredientDetailDTO;
import refrigerator.back.ingredient.application.domain.*;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.ingredient.application.domain.QIngredientImage.ingredientImage;


@Component
@RequiredArgsConstructor
public class IngredientLookUpQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OutIngredientDTO> findIngredientList(LocalDate now, IngredientSearchCondition condition, Pageable pageable) {

        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredientImage.imageFileName))
                .from(ingredient)
                .where(
                        storageCheck(condition.getStorage()),
                        deadlineCheck(now, condition.getDeadline()),    // true면 유통기한 지남, false면 오늘이거나 유통기한 남아있음
                        emailCheck(condition.getEmail()),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .orderBy(orderByConditionallyByDeadline(condition.getDeadline()), ingredient.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<OutIngredientDTO> findIngredientListByDeadline(LocalDate now, Long days, String email) {
        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredientImage.imageFileName))
                .from(ingredient)
                .where(
                        emailCheck(email),
                        expirationDateCheck(now.plusDays(days)),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .fetch();
    }

    public List<OutIngredientDTO> findIngredientListOfAll(String email) {
        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredientImage.imageFileName))
                .from(ingredient)
                .where(
                        emailCheck(email),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .orderBy(ingredient.name.asc())
                .fetch();
    }

    public Optional<OutIngredientDetailDTO> findIngredient(Long id) {
        OutIngredientDetailDTO outIngredientDetailDTO = jpaQueryFactory
                .select(new QOutIngredientDetailDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredient.registrationDate,
                        ingredient.capacity,
                        ingredient.capacityUnit,
                        ingredient.storageMethod,
                        ingredientImage.imageFileName
                ))
                .from(ingredient)
                .where(
                        idCheck(id),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .fetchOne();

        return Optional.ofNullable(outIngredientDetailDTO);
    }

    /** condition */

    private OrderSpecifier<?> orderByConditionallyByDeadline(Boolean deadline) {
        if (deadline){
            return ingredient.expirationDate.desc();
        }
        return ingredient.expirationDate.asc();
    }

    public BooleanExpression expirationDateCheck(LocalDate expirationDate) {
        if(expirationDate == null)
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);

        return ingredient.expirationDate.eq(expirationDate);
    }

    public BooleanExpression emailCheck(String email) {
        if(!hasText(email)) {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }

        return ingredient.email.eq(email);
    }

    public BooleanExpression idCheck(Long id) {
        if(id == null) {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }

        return ingredient.id.eq(id);
    }

    public BooleanExpression storageCheck(IngredientStorageType storage) {
        if(storage == null)
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);

        return ingredient.storageMethod.eq(storage);
    }

    public BooleanExpression deadlineCheck(LocalDate now, boolean deadline) {
        if(now == null)
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);

        return deadline ? ingredient.expirationDate.lt(now) : ingredient.expirationDate.goe(now);
    }
}

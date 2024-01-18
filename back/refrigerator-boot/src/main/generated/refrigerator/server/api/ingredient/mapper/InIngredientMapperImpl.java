package refrigerator.server.api.ingredient.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.value.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.IngredientUnitDTO;
import refrigerator.server.api.ingredient.dto.IngredientDeductionRequestDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-13T16:05:59+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class InIngredientMapperImpl implements InIngredientMapper {

    @Override
    public IngredientSearchCondition toIngredientSearchCondition(IngredientStorageType storage, Boolean deadline, String email) {
        if ( storage == null && deadline == null && email == null ) {
            return null;
        }

        IngredientSearchCondition.IngredientSearchConditionBuilder ingredientSearchCondition = IngredientSearchCondition.builder();

        ingredientSearchCondition.storage( storage );
        ingredientSearchCondition.deadline( deadline );
        ingredientSearchCondition.email( email );

        return ingredientSearchCondition.build();
    }

    @Override
    public IngredientDeductionDTO toIngredientDeductionDTO(IngredientDeductionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        IngredientDeductionDTO.IngredientDeductionDTOBuilder ingredientDeductionDTO = IngredientDeductionDTO.builder();

        ingredientDeductionDTO.name( dto.getName() );
        ingredientDeductionDTO.volume( dto.getVolume() );
        ingredientDeductionDTO.unit( dto.getUnit() );

        return ingredientDeductionDTO.build();
    }

    @Override
    public IngredientUnitDTO toIngredientUnitResponseDTO(RegisteredIngredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientUnitDTO.IngredientUnitDTOBuilder ingredientUnitDTO = IngredientUnitDTO.builder();

        ingredientUnitDTO.unit( ingredient.getUnit() );
        ingredientUnitDTO.name( ingredient.getName() );

        return ingredientUnitDTO.build();
    }
}

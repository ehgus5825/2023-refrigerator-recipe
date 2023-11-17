package refrigerator.server.api.ingredient.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.server.api.ingredient.dto.IngredientDeductionRequestDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.IngredientUnitDTO;


@Mapper(componentModel = "spring")
public interface InIngredientMapper {

    InIngredientMapper INSTANCE = Mappers.getMapper(InIngredientMapper.class);

    IngredientSearchCondition toIngredientSearchCondition(IngredientStorageType storage, Boolean deadline, String email);

    IngredientDeductionDTO toIngredientDeductionDTO(IngredientDeductionRequestDTO dto);

    IngredientUnitDTO toIngredientUnitResponseDTO(RegisteredIngredient ingredient);
}

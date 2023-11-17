package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.application.dto.MyIngredientDto;
import refrigerator.back.recipe.application.port.out.FindMyIngredientTablePort;
import refrigerator.back.recipe.outbound.mapper.OutMyIngredientDtoMapper;
import refrigerator.back.recipe.outbound.mapper.OutMyIngredientDtoMappingCollection;
import refrigerator.back.recipe.outbound.repository.query.MyIngredientSelectQueryRepository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MyIngredientLookUpAdapter implements FindMyIngredientTablePort {

    private final MyIngredientSelectQueryRepository queryRepository;
    private final OutMyIngredientDtoMapper mapper;

    @Override
    public Map<String, MyIngredientDto> findMyIngredients(String memberId) {
        OutMyIngredientDtoMappingCollection collection = queryRepository.selectMyIngredientDto(memberId);
        return collection.makeMyIngredientTable(mapper);
    }
}

package refrigerator.back.recipe_search.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.back.recipe_search.outbound.mapper.OutRecipeSearchDtoMapper;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe_search.application.port.out.FindRecipeSearchDtoPort;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeSearchService implements SearchRecipeUseCase {

    private final FindRecipeSearchDtoPort findRecipeSearchDtoPort;

    @Override
    public List<RecipeSearchDto> search(RecipeSearchCondition condition, int page, int size) {
        condition.parameterCheck();
        return findRecipeSearchDtoPort.search(condition, page, size);
    }


}

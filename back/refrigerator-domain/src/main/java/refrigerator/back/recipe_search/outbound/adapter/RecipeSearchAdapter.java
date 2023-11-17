package refrigerator.back.recipe_search.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.back.recipe_search.outbound.mapper.OutRecipeSearchDtoMapper;
import refrigerator.back.recipe_search.outbound.repository.RecipeSearchSelectQueryRepository;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.port.out.FindRecipeSearchDtoPort;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeSearchAdapter implements FindRecipeSearchDtoPort {

    private final RecipeSearchSelectQueryRepository repository;
    private final OutRecipeSearchDtoMapper mapper;
    private final ImageUrlConvert imageUrlConvert;


    @Override
    public List<RecipeSearchDto> search(RecipeSearchCondition condition, int page, int size) {
        return repository.selectSearchRecipeDtos(condition, PageRequest.of(page, size)).stream()
                .map(recipe -> recipe.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
    }


}

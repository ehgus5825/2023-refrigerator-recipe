package refrigerator.server.api.recipe_search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.server.api.recipe_search.dto.InRecipeBasicListDto;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe_searchword.application.port.in.AddSearchWordUseCase;
import refrigerator.server.api.recipe_search.dto.InRecipeSearchConditionDto;
import refrigerator.server.api.recipe_search.mapper.InRecipeSearchMapper;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final AddSearchWordUseCase addSearchWordUseCase;
    private final InRecipeSearchMapper mapper;
    private final GetMemberEmailUseCase memberInformation;


    @PostMapping("/api/recipe/search")
    public InRecipeBasicListDto<RecipeSearchDto> getRecipeSearchList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "11") int size,
            @RequestParam(value = "searchWord") String searchWord,
            @RequestBody InRecipeSearchConditionDto requestDto){
        List<RecipeSearchDto> result = searchRecipeUseCase.search(mapper.toRecipeSearchCondition(requestDto, searchWord), page, size);
        addSearchWordUseCase.addSearchWord(memberInformation.getMemberEmail(), searchWord);
        return new InRecipeBasicListDto<>(result);
    }




}

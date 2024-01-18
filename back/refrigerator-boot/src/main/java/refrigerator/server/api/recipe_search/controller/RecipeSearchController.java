package refrigerator.server.api.recipe_search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe_searchword.application.port.in.AddSearchWordUseCase;
import refrigerator.server.api.recipe_search.dto.InRecipeSearchConditionDto;
import refrigerator.server.api.recipe_search.mapper.InRecipeSearchMapper;

import javax.validation.Valid;

import java.util.List;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final AddSearchWordUseCase addSearchWordUseCase;
    private final InRecipeSearchMapper mapper;
    private final GetMemberEmailUseCase memberInformation;


    @PostMapping("/api/recipe/search")
    public BasicListResponseDTO<RecipeSearchDto> getRecipeSearchList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "11") Integer size,
            @RequestParam(value = "searchWord") String searchWord,
            @Valid @RequestBody InRecipeSearchConditionDto requestDto,
            BindingResult bindingResult){

        positiveOrZeroCheck(page);
        positiveCheck(size);
        multiCheck(bindingResult);

        List<RecipeSearchDto> result = searchRecipeUseCase.search(mapper.toRecipeSearchCondition(requestDto, searchWord), page, size);
        addSearchWordUseCase.addSearchWord(memberInformation.getMemberEmail(), searchWord);
        return new BasicListResponseDTO<>(result);
    }

}

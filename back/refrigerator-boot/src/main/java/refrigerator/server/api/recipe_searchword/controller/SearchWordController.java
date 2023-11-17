package refrigerator.server.api.recipe_searchword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.recipe_searchword.application.port.in.DeleteSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.in.FindLastSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.in.FindRecommendSearchWordUseCase;


@RestController
@RequiredArgsConstructor
public class SearchWordController {

    private final DeleteSearchWordUseCase deleteSearchWordUseCase;
    private final FindLastSearchWordUseCase findLastSearchWordUseCase;
    private final FindRecommendSearchWordUseCase findRecommendSearchWordUseCase;
    private final GetMemberEmailUseCase memberInformation;


    @GetMapping("/api/recipe/search/words/recommend")
    public BasicListResponseDTO<String> getRecommendSearchWords(){
        String memberId = memberInformation.getMemberEmail();
        return new BasicListResponseDTO<>(
                findRecommendSearchWordUseCase.getRecommendSearchWords(memberId));
    }

    @GetMapping("/api/recipe/search/words/last")
    public BasicListResponseDTO<String> getLastSearchWords(){
        String memberId = memberInformation.getMemberEmail();
        return new BasicListResponseDTO<>(
                findLastSearchWordUseCase.getLastSearchWords(memberId)
        );
    }

    @DeleteMapping("/api/recipe/search/words/delete")
    public void deleteSearchWord(@RequestParam("searchWord") String searchWord){
        deleteSearchWordUseCase.delete(memberInformation.getMemberEmail(), searchWord);
    }

}

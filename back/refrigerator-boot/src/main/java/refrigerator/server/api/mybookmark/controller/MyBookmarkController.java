package refrigerator.server.api.mybookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.mybookmark.application.port.in.*;


@RestController
@RequiredArgsConstructor
public class MyBookmarkController {

    private final AddMyBookmarkUseCase addBookmarkUseCase;
    private final DeleteMyBookmarkUseCase deleteMyBookmarkUseCase;
    private final GetMemberEmailUseCase memberInformation;


    @PostMapping("/api/recipe/{recipeId}/bookmark/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addBookmark(@PathVariable("recipeId") Long recipeId){
        String email = memberInformation.getMemberEmail();
        addBookmarkUseCase.add(email, recipeId);
    }

    @DeleteMapping("/api/recipe/{recipeId}/bookmark/deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookmark(@PathVariable("recipeId") Long recipeId){
        String email = memberInformation.getMemberEmail();
        deleteMyBookmarkUseCase.delete(recipeId, email);
    }

}

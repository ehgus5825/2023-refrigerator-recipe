package refrigerator.server.api.mybookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.mybookmark.application.port.in.*;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
public class MyBookmarkController {

    private final AddMyBookmarkUseCase addBookmarkUseCase;
    private final DeleteMyBookmarkUseCase deleteMyBookmarkUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/recipe/{recipeId}/bookmark/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addBookmark(
             @PathVariable("recipeId") @Positive Long recipeId){

        String email = memberInformation.getMemberEmail();
        addBookmarkUseCase.add(email, recipeId);
    }

    @DeleteMapping("/api/recipe/{recipeId}/bookmark/deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookmark(
             @PathVariable("recipeId") @Positive Long recipeId){

        String email = memberInformation.getMemberEmail();
        deleteMyBookmarkUseCase.delete(recipeId, email);
    }

}

package refrigerator.server.api.myscore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.myscore.application.port.in.CookingUseCase;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@Validated
public class CookingController {

    private final CookingUseCase cookingUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @PostMapping("/api/recipe/{recipeId}/cooking")
    @ResponseStatus(HttpStatus.CREATED)
    public void cooking(@PathVariable("recipeId") @Positive Long recipeId,
                        @RequestParam("score") @PositiveOrZero Double score){

        String memberId = getMemberEmailUseCase.getMemberEmail();
        cookingUseCase.cooking(memberId, recipeId, score);
    }

}

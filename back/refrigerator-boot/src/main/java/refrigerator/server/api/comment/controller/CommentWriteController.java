package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.server.api.comment.dto.request.CommentWriteRequestDto;
import refrigerator.server.api.comment.dto.response.CommentWriteResponseDto;


import javax.validation.Valid;

import static refrigerator.back.comment.exception.CommentExceptionType.EMPTY_CONTENT;
import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class CommentWriteController {

    private final WriteCommentUseCase writeCommentUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/recipe/{recipeId}/comments/write")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentWriteResponseDto write(
            @PathVariable("recipeId") Long recipeId,
            @Valid @RequestBody CommentWriteRequestDto request,
            BindingResult bindingResult){

        positiveCheck(recipeId);
        check(bindingResult, EMPTY_CONTENT);

        String memberId = memberInformation.getMemberEmail();
        Long commentId = writeCommentUseCase.write(
                recipeId,
                memberId,
                request.getContent());
        return CommentWriteResponseDto.builder().commentId(commentId).build();
    }

}

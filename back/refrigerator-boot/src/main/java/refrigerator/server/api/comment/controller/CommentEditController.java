package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.port.in.EditCommentUseCase;
import refrigerator.server.api.comment.dto.request.CommentEditRequestDto;

import javax.validation.Valid;

import static refrigerator.back.comment.exception.CommentExceptionType.EMPTY_CONTENT;
import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;


@RestController
@RequiredArgsConstructor
public class CommentEditController {
    private final EditCommentUseCase editCommentUseCase;

    @PutMapping("/api/comments/{commentId}/edit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(@PathVariable("commentId") Long commentId,
                     @Valid @RequestBody CommentEditRequestDto request,
                     BindingResult bindingResult){

        positiveCheck(commentId);
        check(bindingResult, EMPTY_CONTENT);

        editCommentUseCase.edit(commentId, request.getContent());
    }

}
package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.port.in.DeleteCommentUseCase;

import javax.validation.constraints.Positive;


@RestController
@RequiredArgsConstructor
@Validated
public class CommentDeleteController {

    private final DeleteCommentUseCase deleteCommentUseCase;

    @DeleteMapping("/api/comments/{commentId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("commentId") @Positive Long commentId){

        deleteCommentUseCase.delete(commentId);
    }

}
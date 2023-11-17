package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.port.in.DeleteCommentUseCase;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentDeleteController {

    private final DeleteCommentUseCase deleteCommentUseCase;

    @DeleteMapping("/api/comments/{commentId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("commentId") Long commentId){
        deleteCommentUseCase.delete(commentId);
    }

}

package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.comment.application.port.in.EditCommentUseCase;
import refrigerator.server.api.comment.dto.InCommentEditRequestDto;
import refrigerator.server.api.global.exception.ValidationExceptionHandler;

import static refrigerator.back.comment.exception.CommentExceptionType.NOT_VALID_REQUEST_BODY;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentEditController {
    private final EditCommentUseCase editCommentUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PutMapping("/api/comments/{commentId}/edit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(@PathVariable("commentId") Long commentId,
            @RequestBody InCommentEditRequestDto request,
                     BindingResult bindingResult){
        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);
        String memberId = memberInformation.getMemberEmail();
        editCommentUseCase.edit(memberId, commentId, request.getContent());
    }

}

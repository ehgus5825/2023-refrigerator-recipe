package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.port.in.ChangeCommentHeartCountUseCase;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
public class CommentHeartController {

    private final ChangeCommentHeartCountUseCase changeCommentHeartCountUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    private final CreateCommentHeartNotificationUseCase createCommentHeartNotificationUseCase;

    @PutMapping("/api/comments/{commentId}/heart/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHeartCount(
            @PathVariable("commentId") @Positive Long commentId){

        String memberId = getMemberEmailUseCase.getMemberEmail();
        changeCommentHeartCountUseCase.add(commentId, memberId);

        createCommentHeartNotificationUseCase.createCommentHeartNotification(memberId, commentId);
    }

    @PutMapping("/api/comments/{commentId}/heart/reduce")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reduceHeartCount(
             @PathVariable("commentId") @Positive Long commentId){


        String memberId = getMemberEmailUseCase.getMemberEmail();
        changeCommentHeartCountUseCase.reduce(commentId, memberId);
    }
}
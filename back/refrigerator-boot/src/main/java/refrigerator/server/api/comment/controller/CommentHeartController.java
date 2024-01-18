package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.port.in.ChangeCommentHeartCountUseCase;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.server.api.comment.dto.response.CommentAddResponseDto;

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
    public CommentAddResponseDto addHeartCount(
            @PathVariable("commentId") @Positive Long commentId){

        String memberId = getMemberEmailUseCase.getMemberEmail();
        String peopleId = changeCommentHeartCountUseCase.add(commentId, memberId);

        createCommentHeartNotificationUseCase.createCommentHeartNotification(memberId, commentId);

        return CommentAddResponseDto.builder().peopleId(peopleId).build();
    }

    @PutMapping("/api/comments/{commentId}/heart/reduce")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reduceHeartCount(
             @PathVariable("commentId") @Positive Long commentId,
             @RequestParam("peopleId") @NotBlank String peopleId){

        changeCommentHeartCountUseCase.reduce(commentId, peopleId);
    }
}
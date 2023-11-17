package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.comment.application.port.in.ChangeCommentHeartCountUseCase;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.server.api.comment.dto.InCommentAddResponseDto;


@RestController
@RequiredArgsConstructor
public class CommentHeartController {

    private final ChangeCommentHeartCountUseCase changeCommentHeartCountUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @PutMapping("/api/comments/{commentId}/heart/add")
    @ResponseStatus(HttpStatus.CREATED)
    public InCommentAddResponseDto addHeartCount(@PathVariable("commentId") Long commentId){
        String memberId = getMemberEmailUseCase.getMemberEmail();
        String peopleId = changeCommentHeartCountUseCase.add(commentId, memberId);
        return InCommentAddResponseDto.builder().peopleId(peopleId).build();
        // TODO: 좋아요 알림 전송 메세지 큐 비동기 처리
    }

    @PutMapping("/api/comments/{commentId}/heart/reduce")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reduceHeartCount(@PathVariable("commentId") Long commentId,
                                 @RequestParam("peopleId") String peopleId){
        changeCommentHeartCountUseCase.reduce(commentId, peopleId);
    }

}

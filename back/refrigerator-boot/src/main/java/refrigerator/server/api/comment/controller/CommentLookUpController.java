package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.port.in.FindCommentsUseCase;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.comment.application.dto.InCommentsPreviewResponseDto;
import refrigerator.server.api.comment.dto.InCommentsResponseDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentLookUpController {

    private final FindCommentsUseCase findCommentListUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/recipe/{recipeId}/comments")
    public InCommentsResponseDto findComments(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam(value = "sortCondition", defaultValue = "DATE") String sortCondition,
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        String memberId = memberInformation.getMemberEmail();
        CommentSortCondition condition = CommentSortCondition.valueOf(sortCondition);
        List<CommentDto> comments = findCommentListUseCase.findComments(
                recipeId,
                memberId,
                condition,
                page,
                size);
        List<CommentDto> myComments = findCommentListUseCase.findMyComments(memberId, recipeId);
        return new InCommentsResponseDto(comments, myComments);
    }

    @GetMapping("/api/recipe/{recipeId}/comments/preview")
    public InCommentsPreviewResponseDto findCommentPreview(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam(value = "size", defaultValue = "3") int size){
        String memberId = memberInformation.getMemberEmail();
        return findCommentListUseCase.findCommentsPreview(recipeId, memberId, size);
    }

}

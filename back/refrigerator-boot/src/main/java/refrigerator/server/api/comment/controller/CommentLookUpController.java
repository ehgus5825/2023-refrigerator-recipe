package refrigerator.server.api.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.comment.application.domain.value.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.port.in.FindCommentsUseCase;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.comment.application.dto.InCommentsPreviewResponseDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CommentLookUpController {

    private final FindCommentsUseCase findCommentListUseCase;
    private final GetMemberEmailUseCase memberInformation;


    @GetMapping("/api/recipe/{recipeId}/comments")
    public BasicListResponseDTO<CommentDto> findComments(
             @PathVariable("recipeId") @Positive Long recipeId,
             @RequestParam(value = "sortCondition", defaultValue = "DATE") String sortCondition,
             @RequestParam(value = "page", defaultValue = "0")  @PositiveOrZero Integer page,
             @RequestParam(value = "size", defaultValue = "11") @Positive Integer size){

        if (!sortCondition.equals("DATE") && !sortCondition.equals("HEART")){
            throw new BusinessException(CommentExceptionType.INPUT_ERROR_SORTCONDITION);
        }

        String memberId = memberInformation.getMemberEmail();
        CommentSortCondition condition = CommentSortCondition.valueOf(sortCondition);

        return new BasicListResponseDTO<>(findCommentListUseCase.findComments(
                recipeId,
                memberId,
                condition,
                page,
                size));
    }

    @GetMapping("/api/recipe/{recipeId}/comments/own")
    public BasicListResponseDTO<CommentDto> findMyComments(
            @PathVariable("recipeId") @Positive Long recipeId){

        String memberId = memberInformation.getMemberEmail();

        return new BasicListResponseDTO<>(findCommentListUseCase.findMyComments(memberId, recipeId));
    }

    @GetMapping("/api/recipe/{recipeId}/comments/preview")
    public InCommentsPreviewResponseDto findCommentPreview(
             @PathVariable("recipeId") @Positive Long recipeId,
             @RequestParam(value = "size", defaultValue = "3") @Positive Integer size){

        String memberId = memberInformation.getMemberEmail();
        return findCommentListUseCase.findCommentsPreview(recipeId, memberId, size);
    }

}

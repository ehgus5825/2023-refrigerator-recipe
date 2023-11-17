package refrigerator.back.comment.application.port.in;


import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.dto.InCommentsPreviewResponseDto;

import java.util.List;

public interface FindCommentsUseCase {
    List<CommentDto> findComments(Long recipeId, String memberId, CommentSortCondition sortCondition, int page, int size);
    InCommentsPreviewResponseDto findCommentsPreview(Long recipeId, String memberId, int size);
    List<CommentDto> findMyComments(String memberId, Long recipeId);
}

package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.value.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;

import java.util.List;

public interface FindCommentPort {
    List<CommentDto> findComments(Long recipeId, String memberId, CommentSortCondition sortCondition, int page, int size);
    List<CommentDto> findPreviewComments(Long recipeId, int size);
    List<CommentDto> findMyComments(String memberId, Long recipeId);
}

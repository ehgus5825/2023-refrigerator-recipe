package refrigerator.back.comment.application.port.in;

import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;

public interface FindCommentPreviewListUseCase {
    InCommentListDTO findCommentPreviews(Long recipeId, int size);
}

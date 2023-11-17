package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;
import refrigerator.back.comment.application.dto.InCommentsPreviewResponseDto;
import refrigerator.back.comment.application.port.in.FindCommentsUseCase;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.FindCommentPort;
import refrigerator.back.comment.application.port.out.FindNumberOfCommentsPort;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentLookUpService implements FindCommentsUseCase {

    private final FindCommentPort findCommentPort;
    private final FindNumberOfCommentsPort findNumberOfCommentsPort;
    private final FindCommentHeartPeoplePort commentHeartPeoplePort;

    @Override
    public List<CommentDto> findComments(Long recipeId, String memberId, CommentSortCondition sortCondition, int page, int size) {
        return mapping(memberId, findCommentPort.findComments(recipeId, memberId, sortCondition, page, size));
    }

    @Override
    public InCommentsPreviewResponseDto findCommentsPreview(Long recipeId, String memberId, int size) {
        List<CommentDto> comments = mapping(memberId, findCommentPort.findPreviewComments(recipeId, memberId, size));
        Integer count = findNumberOfCommentsPort.getNumber(recipeId);
        return new InCommentsPreviewResponseDto(comments, count);
    }

    @Override
    public List<CommentDto> findMyComments(String memberId, Long recipeId) {
        return mapping(memberId, findCommentPort.findMyComments(memberId, recipeId));
    }

    private List<CommentDto> mapping(String memberId, List<CommentDto> comments){
        Map<Long, CommentHeartPeopleDto> peoples = commentHeartPeoplePort.findPeopleMap(memberId);
        comments.forEach(comment -> comment.isLiked(peoples));
        return comments;
    }

}

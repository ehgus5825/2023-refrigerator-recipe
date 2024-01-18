package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.service.CommentTimeService;
import refrigerator.back.comment.outbound.dto.OutCommentDto;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;
import refrigerator.back.comment.outbound.repository.query.CommentSelectQueryRepository;
import refrigerator.back.comment.application.domain.value.CommentSortCondition;
import refrigerator.back.comment.application.port.out.FindCommentPort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentLookUpAdapter implements FindCommentPort {

    private final CommentSelectQueryRepository queryRepository;
    private final CommentTimeService commentTimeService;
    private final OutCommentMapper mapper;

    @Override
    public List<CommentDto> findComments(Long recipeId,
                                         String memberId,
                                         CommentSortCondition sortCondition,
                                         int page, int size) {
        return mapping(queryRepository.selectComments(recipeId, memberId, PageRequest.of(page, size), sortCondition));
    }

    @Override
    public List<CommentDto> findPreviewComments(Long recipeId, int size) {
        return mapping(queryRepository.selectPreviewComments(recipeId, PageRequest.of(0, size)));
    }

    @Override
    public List<CommentDto> findMyComments(String memberId, Long recipeId) {
        Pageable page = PageRequest.of(0, 11);
        return mapping(queryRepository.selectMyComments(memberId, recipeId, page));
    }

    private List<CommentDto> mapping(List<OutCommentDto> comments){
        return comments.stream()
                .map(comment -> comment.mapping(mapper, commentTimeService))
                .collect(Collectors.toList());
    }

}

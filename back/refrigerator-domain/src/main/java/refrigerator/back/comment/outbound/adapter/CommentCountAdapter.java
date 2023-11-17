package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.port.out.FindNumberOfCommentsPort;
import refrigerator.back.comment.outbound.repository.query.CommentSelectQueryRepository;

@Repository
@RequiredArgsConstructor
public class CommentCountAdapter implements FindNumberOfCommentsPort {

    private final CommentSelectQueryRepository queryRepository;

    @Override
    public Integer getNumber(Long recipeId) {
        return queryRepository.selectCommentsCount(recipeId).getNumber();
    }

}

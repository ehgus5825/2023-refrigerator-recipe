package refrigerator.back.comment.application.port.batch;

import java.util.List;

public interface DeleteCommentBatchPort {

    Long deleteCommentHeart(List<Long> ids);

}

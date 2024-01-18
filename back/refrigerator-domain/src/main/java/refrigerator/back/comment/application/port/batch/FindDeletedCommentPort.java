package refrigerator.back.comment.application.port.batch;

import java.util.List;

public interface FindDeletedCommentPort {

    List<Long> findDeletedComment();

}
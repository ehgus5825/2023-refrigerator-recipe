package refrigerator.back.comment.application.port.out;

import java.time.LocalDateTime;

public interface ModifyCommentPort {
    void modifyContent(Long id, String content, LocalDateTime now);
}

package refrigerator.back.comment.application.port.in;

public interface ChangeCommentHeartCountUseCase {
    String add(Long commentId, String memberId);
    void reduce(Long commentId, String peopleId);
}

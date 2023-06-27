package refrigerator.back.comment.application.port.out;


import refrigerator.back.comment.application.domain.CommentHeartPeople;

public interface WriteCommentHeartPeoplePort {
    Long addHeartPeople(CommentHeartPeople commentHeartPeople);
    void removeHeartPeople(String memberId, Long commentId);
}
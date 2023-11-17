package refrigerator.back.comment.application.port.out;

public interface CheckExistCommentHeartPeoplePort {
    Boolean checkByCommentIdAndMemberId(Long commentId, String memberId);
    Boolean checkByPeopleId(String peopleId);
}

package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;

public interface CheckExistCommentHeartPeoplePort {
    Boolean checkByCommentIdAndMemberIdExist(Long commentId, String memberId);
}

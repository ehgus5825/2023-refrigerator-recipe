package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.domain.value.CommentHeartValue;
import refrigerator.back.comment.application.port.in.ChangeCommentHeartCountUseCase;
import refrigerator.back.comment.application.port.out.*;
import refrigerator.back.global.common.RandomUUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentHeartChangeService implements ChangeCommentHeartCountUseCase {

    private final CheckExistCommentHeartPeoplePort checkExistCommentHeartPeoplePort;
    private final ChangeCommentHeartCountPort changeCommentHeartCountPort;
    private final SaveCommentHeartPeoplePort saveCommentHeartPeoplePort;
    private final RemoveCommentHeartPeoplePort removeCommentHeartPeoplePort;
    private final FindCommentHeartPeoplePort commentHeartPeoplePort;
    private final RandomUUID randomUUID;

    @Override
    public void add(Long commentId, String memberId) {
        Boolean isExistPeople = checkExistCommentHeartPeoplePort.checkByCommentIdAndMemberIdExist(commentId, memberId);
        CommentHeartPeople commentHeartPeople = CommentHeartPeople.add(isExistPeople, randomUUID, commentId, memberId);
        saveCommentHeartPeoplePort.save(commentHeartPeople);

        changeCommentHeartCountPort.change(commentId, CommentHeartValue.ADD);
    }

    @Override
    public void reduce(Long commentId, String memberId) {
        Boolean isExistPeople = checkExistCommentHeartPeoplePort.checkByCommentIdAndMemberIdExist(commentId, memberId);
        CommentHeartPeople.checkReduceRequest(isExistPeople);

        CommentHeartPeople commentHeartPeople = commentHeartPeoplePort.findByCommentIdAndMemberId(commentId, memberId);
        removeCommentHeartPeoplePort.remove(commentHeartPeople);

        changeCommentHeartCountPort.change(commentId, CommentHeartValue.REDUCE);
    }
}

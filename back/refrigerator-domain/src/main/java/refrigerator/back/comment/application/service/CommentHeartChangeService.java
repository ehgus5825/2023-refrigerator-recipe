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
    private final RandomUUID randomUUID;

    @Override
    public String add(Long commentId, String memberId) {
        Boolean isExistPeople = checkExistCommentHeartPeoplePort.checkByCommentIdAndMemberId(commentId, memberId);
        CommentHeartPeople commentHeartPeople = CommentHeartPeople.add(isExistPeople, randomUUID, commentId, memberId);
        saveCommentHeartPeoplePort.save(commentHeartPeople);
        changeCommentHeartCountPort.change(commentId, CommentHeartValue.ADD);
        return commentHeartPeople.getId();
    }

    @Override
    public void reduce(Long commentId, String peopleId) {
        Boolean isExistPeople = checkExistCommentHeartPeoplePort.checkByPeopleId(peopleId);
        CommentHeartPeople.checkReduceRequest(isExistPeople);
        removeCommentHeartPeoplePort.remove(peopleId);
        changeCommentHeartCountPort.change(commentId, CommentHeartValue.REDUCE);
    }
}

package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.common.RandomUUID;
import refrigerator.back.global.exception.BusinessException;

import javax.persistence.Id;

@Getter
@RedisHash
@Slf4j
public class CommentHeartPeople {

    @Id
    private String id;

    @Indexed
    private Long commentId;

    @Indexed
    private String memberId;

    public CommentHeartPeople(String id, Long commentId, String memberId) {
        this.id = id;
        this.commentId = commentId;
        this.memberId = memberId;
    }

    public static CommentHeartPeople add(Boolean isExistPeople,
                                         RandomUUID uuid, Long commentId, String memberId){
        CommentHeartPeople people = new CommentHeartPeople(uuid.getUUID().substring(0, 8), commentId, memberId);
        people.checkAddRequest(isExistPeople);
        return people;
    }

    public void checkAddRequest(Boolean isExistPeople){
        if (isExistPeople){
            log.info("사용자가 중복된 하트 수 증가 요청을 보냈습니다.");
            throw new BusinessException(CommentExceptionType.DUPLICATE_HEART_REQUEST);
        }
    }

    public static void checkReduceRequest(Boolean isExistPeople){
        if (!isExistPeople){
            log.info("사용자가 중복된 하트 수 감소 요청을 보냈습니다.");
            throw new BusinessException(CommentExceptionType.DUPLICATE_HEART_REQUEST);
        }
    }

    public CommentHeartPeopleDto mappingDto(){
        return new CommentHeartPeopleDto(id, commentId);
    }
}

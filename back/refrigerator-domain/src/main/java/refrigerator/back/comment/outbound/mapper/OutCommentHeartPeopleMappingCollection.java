package refrigerator.back.comment.outbound.mapper;

import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutCommentHeartPeopleMappingCollection {

    private final List<CommentHeartPeople> peoples;

    public OutCommentHeartPeopleMappingCollection(List<CommentHeartPeople> peoples) {
        this.peoples = peoples;
    }

    public Map<Long, CommentHeartPeopleDto> getPeopleMap(){
        Map<Long, CommentHeartPeopleDto> map = new HashMap<>();
        peoples.forEach(people -> {
            map.put(people.getCommentId(), people.mappingDto());
        });
        return map;
    }
}

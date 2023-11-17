package refrigerator.back.comment.outbound.repository.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import refrigerator.back.comment.application.domain.CommentHeartPeople;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
@Disabled
@Slf4j
class CommentHeartPeopleRedisRepositoryTest {

    @Autowired CommentHeartPeopleRedisRepository repository;

    @BeforeEach
    void clear(){
        repository.deleteAll();
    }

    @Test
    void findByCommentIdAndMemberId() {
        // given
        CommentHeartPeople people = new CommentHeartPeople("id", 1L, "email");
        repository.save(people);
        // when
        Optional<CommentHeartPeople> result = repository.findByCommentIdAndMemberId(1L, "email");
        // then
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void findByMemberId(){
        // given
        CommentHeartPeople people1 = new CommentHeartPeople("id1", 1L, "email");
        CommentHeartPeople people2 = new CommentHeartPeople("id2", 2L, "email");
        repository.saveAll(Arrays.asList(people1, people2));
        // when
        List<CommentHeartPeople> result = repository.findByMemberId("email");
        // then
        assertEquals(2, result.size());
    }
}
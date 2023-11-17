package refrigerator.back.global.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import refrigerator.back.comment.application.service.CommentTimeService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TimeServiceTest {

    @Test
    void replace() {
        // given
        CommentTimeService commentTimeService = new TimeService(
                () -> LocalDateTime.of(2023, 9, 18, 1, 1)
        );
        LocalDateTime commentWriteDateTime = LocalDateTime.of(2023, 7, 18, 1, 1);
        // when
        String result = commentTimeService.replace(commentWriteDateTime);
        // then
        log.info("result = {}", result);
        assertEquals("2 달 전", result);
    }
}
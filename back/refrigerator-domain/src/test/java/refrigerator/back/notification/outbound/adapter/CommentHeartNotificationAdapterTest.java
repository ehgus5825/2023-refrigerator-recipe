package refrigerator.back.notification.outbound.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapperConfig;
import refrigerator.back.notification.outbound.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, NotificationQueryRepository.class,
        CommentHeartNotificationAdapter.class, OutNotificationMapperConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit("/comment.sql")
class CommentHeartNotificationAdapterTest {

    @Autowired CommentHeartNotificationAdapter commentHeartNotificationAdapter;

    @Test
    @DisplayName("이메일에 따른 닉네임 조회 => 예외 확인")
    void getNickname() {
        assertThatThrownBy(() -> commentHeartNotificationAdapter.getNickname("test123@gmail.com"))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("댓글 id에 따른 저자 및 레시피 id 조회 => 예외 확인")
    void getDetails() {
        assertThatThrownBy(() -> commentHeartNotificationAdapter.getDetails(-1L))
                .isInstanceOf(BusinessException.class);

        assertThat(commentHeartNotificationAdapter.getDetails(1L))
                .isInstanceOf(CommentNotificationDTO.class);
    }
}
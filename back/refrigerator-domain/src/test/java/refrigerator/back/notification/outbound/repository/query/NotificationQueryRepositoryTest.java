package refrigerator.back.notification.outbound.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, NotificationQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit({"/member.sql", "/comment.sql"})
@Slf4j
class NotificationQueryRepositoryTest {

    @Autowired TestEntityManager em;

    @Autowired NotificationQueryRepository notificationQueryRepository;

    @BeforeEach
    void setUp() {

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        Notification.NotificationBuilder builder = Notification.builder()
                .message("test message")
                .path("/")
                .readStatus(false)
                .memberId("dhtest@gmail.com")
                .method(BasicHttpMethod.GET.name());


        for (NotificationType value : NotificationType.values()) {
            for (int i = 0; i < 2; i++) {
                em.persist(builder
                        .type(value)
                        .createDate(now.plusHours(i+1))
                        .build());
            }
        }
    }
    @Test
    @DisplayName("알림 단건 조회 (읽음 상태 변경)")
    void updateReadStatusTest() {

        // given
        Notification notification = Notification.builder()
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .memberId("email123@gmail.com")
                .createDate(LocalDateTime.of(2023,1,1,0,0,0))
                .method(BasicHttpMethod.GET.name())
                .build();

        Long id = em.persistAndGetId(notification, Long.class);

        // when
        Long execute = notificationQueryRepository.updateReadStatus(id, true);

        // then
        assertThat(execute).isEqualTo(1);
        assertThat(em.find(Notification.class, id).isReadStatus()).isTrue();
    }

    @Test
    @DisplayName("알림 목록 조회")
    void findNotificationListTest() {

        // given
        String email = "dhtest@gmail.com";

        // when
        List<Notification> list = notificationQueryRepository.findNotificationList(email, PageRequest.of(0, 20));

        // then
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("알림 목록 조회, 정렬 테스트")
    void findNotificationListSortTest() {

        // given
        String email = "dhtest@gmail.com";

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        // when
        List<Notification> list = notificationQueryRepository.findNotificationList(email, PageRequest.of(0, 20));

        // then
        assertThat(list.size()).isEqualTo(10);
        assertThat(list.get(0).getType()).isEqualTo(NotificationType.ONE_DAY_BEFORE_EXPIRATION);
        assertThat(list.get(1).getType()).isEqualTo(NotificationType.ONE_DAY_BEFORE_EXPIRATION);
        assertThat(list.get(2).getType()).isEqualTo(NotificationType.THREE_DAY_BEFORE_EXPIRATION);
        assertThat(list.get(3).getType()).isEqualTo(NotificationType.THREE_DAY_BEFORE_EXPIRATION);
        assertThat(list.get(4).getCreateDate()).isEqualTo(now.plusHours(1));
        assertThat(list.get(5).getCreateDate()).isEqualTo(now.plusHours(1));
    }

    @Test
    @DisplayName("이메일에 따른 닉네임 조회")
    void getNicknameTest() {

        notificationQueryRepository.getNickname("dhtest@gmail.com")
                .ifPresent(nickname -> {
                    log.info("enter");
                    assertThat(nickname).isEqualTo("도현");
                });
    }

    @Test
    @DisplayName("댓글 id에 따른 저자 및 레시피 id 조회")
    void getDetailsTest() {

        notificationQueryRepository.getDetails(1L)
                .ifPresent(dto -> {
                    log.info("enter");
                    assertThat(dto.getAuthorId()).isEqualTo("jktest101@gmail.com");
                    assertThat(dto.getRecipeId()).isEqualTo(1L);
                });
    }

}
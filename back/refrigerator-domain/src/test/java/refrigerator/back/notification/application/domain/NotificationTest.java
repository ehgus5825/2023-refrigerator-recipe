package refrigerator.back.notification.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


class NotificationTest {

    @Test
    @DisplayName("알림 도메인 테스트")
    void notificationTest() {

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Notification notification = Notification.builder()
                .id(1L)
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .createDate(now)
                .memberId("email123@gmail.com")
                .method(BasicHttpMethod.GET.name())
                .build();

        assertThat(notification.getId()).isEqualTo(1L);
        assertThat(notification.getMessage()).isEqualTo("test message");
        assertThat(notification.getType()).isEqualTo(NotificationType.HEART);
        assertThat(notification.getPath()).isEqualTo("/");
        assertThat(notification.isReadStatus()).isEqualTo(false);
        assertThat(notification.getCreateDate()).isEqualTo(now);
        assertThat(notification.getMemberId()).isEqualTo("email123@gmail.com");
        assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
    }

    @Test
    @DisplayName("알림 도메인 생성 테스트")
    void createNotificationTest() {

        CurrentTime<LocalDateTime> currentTime = () -> LocalDateTime.of(2023,1,1, 0, 0, 0);

        Notification notification = Notification.create(
                NotificationType.HEART,
                "/recipe/comment?recipeId=1&commentID=1",
                "email123@gmain.com",
                BasicHttpMethod.GET.name(),
                currentTime.now()
        );

        assertThat(notification.getType()).isEqualTo(NotificationType.HEART);
        assertThat(notification.getPath()).isEqualTo("/recipe/comment?recipeId=1&commentID=1");
        assertThat(notification.getMemberId()).isEqualTo("email123@gmain.com");
        assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
        assertThat(notification.getCreateDate()).isEqualTo(currentTime.now());
    }

    @Test
    @DisplayName("댓글 좋아요 알림 메시지 테스트")
    void createCommentHeartMessageTest() {
        Notification notification = new Notification();
        notification.createCommentHeartMessage("email123@gmain.com");

        assertThat(notification.getMessage()).isEqualTo("email123@gmain.com 님이 좋아요를 눌렀습니다.");
    }

    @Test
    @DisplayName("유통기한 임박 알림 메시지 테스트")
    void createExpirationDateMessageTest() {
        Notification notification = new Notification();
        notification.createExpirationDateMessage("사과", 2l, 3);
        assertThat(notification.getMessage()).isEqualTo("사과 외 1개 식재료의 소비기한이 3일 남았습니다. 식재료 확인하러가기!");

        notification.createExpirationDateMessage("사과", 1l, 3);
        assertThat(notification.getMessage()).isEqualTo("사과의 소비기한이 3일 남았습니다. 식재료 확인하러가기!");

        assertThatThrownBy(() -> notification.createExpirationDateMessage("사과", 0L, 3))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("요청 식재료 추가 가능 알림 메시지 테스트")
    void createIngredientMessage() {
        Notification notification = new Notification();
        notification.createIngredientMessage("사과");
        assertThat(notification.getMessage()).isEqualTo("회원님이 요청했던 사과를 이제 냉장고에 담을 수 있습니다. (식재료 추가하기)");
    }

    @Test
    @DisplayName("공지사항 알림 메시지 테스트")
    void createNoticeMessage() {
        Notification notification = new Notification();
        notification.createNoticeMessage("안녕하세요");
        assertThat(notification.getMessage()).isEqualTo("공지사항이 추가되었어요! '안녕하세요'");

    }
}
package refrigerator.back.notification.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.port.out.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNotificationServiceTest {

    @InjectMocks CreateNotificationService createNotificationService;

    @Mock FindSenderNicknamePort findSenderNicknamePort;

    @Mock FindCommentDetailsPort commentDetailsPort;

    @Mock SaveNotificationPort saveNotificationPort;

    @Mock SaveMemberNotificationPort saveMemberNotificationPort;

    @Mock FindMemberNotificationPort findMemberNotificationPort;

    @Mock CurrentTime<LocalDateTime> currentTime;

    @Test
    @DisplayName("댓글 좋아요 알림 생성 전체 로직")
    void createCommentHeartNotificationTest() {

        String senderId = "email123@gmail.com";
        Long commentId = 1L;

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        given(currentTime.now())
                .willReturn(now);

        given(findSenderNicknamePort.getNickname(senderId))
                .willReturn("익명1");

        CommentNotificationDTO commentDto = CommentNotificationDTO.builder()
                .authorId("email456@gmail.com")
                .recipeId(1L)
                .build();

        given(commentDetailsPort.getDetails(1L))
                .willReturn(commentDto);

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(commentDto.getAuthorId())
                .sign(false)
                .build();

        given(findMemberNotificationPort.getMemberNotification(commentDto.getAuthorId()))
                .willReturn(memberNotification);

        given(saveMemberNotificationPort.save(memberNotification))
                .willReturn("1");

        given(saveNotificationPort.saveNotification(any()))
                .willReturn(1L);

        assertThat(createNotificationService.createCommentHeartNotification(senderId, commentId))
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("댓글 좋아요 알림 생성")
    void madeNotificationTest() {
        Long commentId = 1L;

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        String senderNickname = "nick";

        CommentNotificationDTO commentDto = CommentNotificationDTO.builder()
                .authorId("email456@gmail.com")
                .recipeId(1L)
                .build();

        given(currentTime.now())
                .willReturn(now);

        Notification notification = createNotificationService.madeNotification(commentId, commentDto, senderNickname);
        assertThat(notification.getType()).isEqualTo(NotificationType.HEART);
        assertThat(notification.getPath()).isEqualTo("/recipe/comment?recipeId=" + commentDto.getRecipeId() + "&commentID=" + commentId);
        assertThat(notification.getMemberId()).isEqualTo(commentDto.getAuthorId());
        assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
        assertThat(notification.getMessage()).isEqualTo(senderNickname + " 님이 좋아요를 눌렀습니다.");
        assertThat(notification.getCreateDate()).isEqualTo(now);
    }
}
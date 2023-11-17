package refrigerator.back.notification.outbound.repository.query;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import refrigerator.back.notification.outbound.dto.OutCommentNotificationDTO;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.outbound.dto.QOutCommentNotificationDTO;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static refrigerator.back.comment.application.domain.QComment.comment;
import static refrigerator.back.member.application.domain.QMember.member;
import static refrigerator.back.notification.application.domain.QNotification.*;

@Component
@RequiredArgsConstructor
public class NotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public Long updateReadStatus(Long notificationId, boolean status) {

        Long num = jpaQueryFactory.update(notification)
                .set(notification.readStatus, status)
                .where(notification.id.eq(notificationId))
                .execute();

        em.flush();
        em.clear();

        return num;
    }

    public List<Notification> findNotificationList(String memberId, Pageable pageable) {

        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(notification.type.eq(NotificationType.ONE_DAY_BEFORE_EXPIRATION)).then(3)
                .when(notification.type.eq(NotificationType.THREE_DAY_BEFORE_EXPIRATION)).then(2)
                .otherwise(1);

        return jpaQueryFactory.selectFrom(notification)
                .where(notification.memberId.eq(memberId))
                .orderBy(rankPath.desc(), notification.createDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /** CommentHeart */

    public Optional<String> getNickname(String memberId) {

        return Optional.ofNullable(
                jpaQueryFactory
                .select(member.nickname)
                .from(member)
                .where(member.email.eq(memberId))
                .fetchOne());
    }

    public Optional<OutCommentNotificationDTO> getDetails(Long commentId) {

        return Optional.ofNullable(
                jpaQueryFactory
                .select(new QOutCommentNotificationDTO(comment.writerId, comment.recipeId))
                .from(comment)
                .where(comment.commentId.eq(commentId))
                .fetchOne());
    }
}

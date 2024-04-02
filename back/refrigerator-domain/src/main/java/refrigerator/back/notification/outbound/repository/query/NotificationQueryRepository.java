package refrigerator.back.notification.outbound.repository.query;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.WriteQueryResultType;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.outbound.dto.OutCommentNotificationDTO;
import refrigerator.back.notification.outbound.dto.OutNotificationDTO;
import refrigerator.back.notification.outbound.dto.QOutCommentNotificationDTO;
import refrigerator.back.notification.outbound.dto.QOutNotificationDTO;
import refrigerator.back.recipe.application.domain.entity.QRecipe;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static refrigerator.back.comment.application.domain.entity.QComment.comment;
import static refrigerator.back.global.exception.WriteQueryResultType.*;
import static refrigerator.back.member.application.domain.entity.QMember.member;
import static refrigerator.back.notification.application.domain.entity.QNotification.notification;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.*;


@Component
@RequiredArgsConstructor
public class NotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     *
     * @param notificationId
     * @param status
     * @return
     */
    public WriteQueryResultType updateReadStatus(Long notificationId, boolean status) {

        long num = jpaQueryFactory.update(notification)
                .set(notification.readStatus, status)
                .where(notification.id.eq(notificationId))
                .execute();

        em.flush(); //
        em.clear();

        return findTypeByResult(num);
    }

    /**
     *
     * @param memberId
     * @param pageable
     * @return
     */
    public List<OutNotificationDTO> findNotificationList(String memberId, Pageable pageable) {

        return jpaQueryFactory.select(new QOutNotificationDTO(
                        notification.id,
                        notification.message,
                        notification.type,
                        notification.createDate,
                        notification.path,
                        notification.readStatus
                ))
                .from(notification)
                .where(
                        notification.memberId.eq(memberId),
                        notification.type.ne(NotificationType.ONE_DAY_BEFORE_EXPIRATION),
                        notification.type.ne(NotificationType.THREE_DAY_BEFORE_EXPIRATION))
                .orderBy(notification.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<OutNotificationDTO> findExpirationNotificationList(String memberId) {
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(notification.type.eq(NotificationType.ONE_DAY_BEFORE_EXPIRATION)).then(3)
                .when(notification.type.eq(NotificationType.THREE_DAY_BEFORE_EXPIRATION)).then(2)
                .otherwise(1);

        return jpaQueryFactory.select(new QOutNotificationDTO(
                        notification.id,
                        notification.message,
                        notification.type,
                        notification.createDate,
                        notification.path,
                        notification.readStatus
                ))
                .from(notification)
                .where(
                        notification.memberId.eq(memberId),
                        notification.type.eq(NotificationType.ONE_DAY_BEFORE_EXPIRATION)
                                .or(notification.type.eq(NotificationType.THREE_DAY_BEFORE_EXPIRATION))
                )
                .orderBy(rankPath.desc())
                .fetch();
    }

    /**
     *
     * @param memberId
     * @return
     */
    public Optional<String> getNickname(String memberId) {

        return Optional.ofNullable(
                jpaQueryFactory
                .select(member.nickname)
                .from(member)
                .where(member.email.eq(memberId))
                .fetchOne());
    }

    /**
     *
     * @param commentId
     * @return
     */
    public Optional<OutCommentNotificationDTO> getDetails(Long commentId) {
        return Optional.ofNullable(
                jpaQueryFactory
                .select(new QOutCommentNotificationDTO(comment.writerId, comment.recipeId, recipe.recipeName))
                .from(comment)
                .where(comment.commentId.eq(commentId))
                .leftJoin(recipe).on(recipe.recipeId.eq(comment.recipeId))
                .fetchOne());
    }
}

package refrigerator.back.notification.outbound.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.application.domain.NotificationType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static refrigerator.back.notification.application.domain.QNotification.notification;
import static refrigerator.back.notification.exception.NotificationExceptionType.*;

@Component
@RequiredArgsConstructor
public class NotificationBatchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;


    // option이 true이면 type이 EXPIRATION_DATE인 알림 삭제
    // option이 false이면 type이 EXPIRATION_DATE가 아닌 알림 삭제
    @Transactional
    public Long deleteNotification(Boolean option, LocalDateTime date) {

        long row = jpaQueryFactory
                .delete(notification)
                .where(deleteOption(option), createDateLtCheck(date))
                .execute();

        em.flush();
        em.clear();

        return row;
    }

    @Transactional
    public Long deleteTestNotification(LocalDateTime date) {

        long row = jpaQueryFactory
                .delete(notification)
                .where(createDateGoeCheck(date))
                .execute();

        em.flush();
        em.clear();

        return row;
    }

    public BooleanExpression createDateLtCheck(LocalDateTime date) {
        if(date == null)
            throw new BusinessException(NOTIFICATION_DELETE_FAIL);

        return notification.createDate.lt(date);
    }

    public BooleanExpression createDateGoeCheck(LocalDateTime date) {
        if(date == null)
            throw new BusinessException(NOTIFICATION_DELETE_FAIL);

        return notification.createDate.goe(date);
    }

    public BooleanExpression deleteOption(boolean option) {
        if(option) {
            return notification.type.eq(NotificationType.ONE_DAY_BEFORE_EXPIRATION)
                    .or(notification.type.eq(NotificationType.THREE_DAY_BEFORE_EXPIRATION));
        }

        return notification.type.ne(NotificationType.ONE_DAY_BEFORE_EXPIRATION)
                .and(notification.type.ne(NotificationType.THREE_DAY_BEFORE_EXPIRATION));
    }
}

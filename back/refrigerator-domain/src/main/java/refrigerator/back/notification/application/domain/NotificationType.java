package refrigerator.back.notification.application.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

    HEART("좋아요"),
    NOTICE("공지사항"),
    INGREDIENT("식재료"),
    ONE_DAY_BEFORE_EXPIRATION("유통기한 1일 전"),
    THREE_DAY_BEFORE_EXPIRATION("유통기한 3일 전");

    @JsonValue
    private final String typeName;

}

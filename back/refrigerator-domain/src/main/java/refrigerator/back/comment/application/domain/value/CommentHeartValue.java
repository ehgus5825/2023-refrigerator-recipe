package refrigerator.back.comment.application.domain.value;

import lombok.Getter;

@Getter
public enum CommentHeartValue {
    ADD(1),
    REDUCE(-1)
    ;

    private final int value;

    CommentHeartValue(int value) {
        this.value = value;
    }
}

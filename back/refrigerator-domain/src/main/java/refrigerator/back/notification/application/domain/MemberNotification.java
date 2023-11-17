package refrigerator.back.notification.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class MemberNotification {

    @Id
    private String id;

    @Indexed
    private String memberId;

    private Boolean sign;

    public void setSign(Boolean sign){
        this.sign = sign;
    }
}

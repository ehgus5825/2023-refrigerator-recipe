package refrigerator.back.authentication.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;

import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@RedisHash(timeToLive = 43200L * 60 * 1000) // ttl = 30일
public class RefreshToken implements Serializable {

    @Id
    private String id;

    @Getter
    @Indexed
    private String token;

    private Boolean accessFlag;

    public void toInvalidate(){
        if (!this.accessFlag){
            throw new BusinessException(AuthenticationExceptionType.FAIL_ACCESS_BY_TOKEN);
        }
        this.accessFlag = false;
    }

    public boolean isValidated(){
        return this.accessFlag;
    }

}

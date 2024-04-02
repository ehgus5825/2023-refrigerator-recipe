package refrigerator.back.notification.outbound.repository.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import refrigerator.back.notification.application.domain.entity.MemberNotification;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Slf4j
class MemberNotificationPersistenceRepositoryTest {

    @Autowired MemberNotificationPersistenceRepository repository;

    @Test
    @DisplayName("memberId로 조회 테스트")
    void findByMemberIdTest(){

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .email(memberId)
                .sign(false)
                .build();

        repository.save(memberNotification);

        repository.findByEmail(memberId).ifPresent(
                mn -> {
                    log.info("enter");
                    assertThat(mn.getEmail()).isEqualTo(memberNotification.getEmail());
                    assertThat(mn.getSign()).isEqualTo(memberNotification.getSign());
                }
        );
    }
}
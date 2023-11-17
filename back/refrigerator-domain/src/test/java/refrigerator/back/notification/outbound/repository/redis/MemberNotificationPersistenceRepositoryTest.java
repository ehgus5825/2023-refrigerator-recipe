package refrigerator.back.notification.outbound.repository.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import refrigerator.back.notification.application.domain.MemberNotification;

import static org.assertj.core.api.Assertions.*;




@DataRedisTest
@Slf4j
class MemberNotificationPersistenceRepositoryTest {

    @Autowired MemberNotificationPersistenceRepository repository;

    @BeforeEach
    void clear(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("memberId로 조회 테스트")
    void findByMemberIdTest(){

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(memberId)
                .sign(false)
                .build();

        repository.save(memberNotification);

        repository.findByMemberId(memberId).ifPresent(
                mn -> {
                    log.info("enter");
                    assertThat(mn.getMemberId()).isEqualTo(memberNotification.getMemberId());
                    assertThat(mn.getSign()).isEqualTo(memberNotification.getSign());
                }
        );
    }
}
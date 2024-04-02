package refrigerator.back.notification.outbound.adapter;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;
import refrigerator.back.notification.application.domain.entity.MemberNotification;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, MemberNotificationAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class MemberNotificationAdapterTest {

    @Autowired MemberNotificationAdapter adapter;

    @Autowired MemberNotificationPersistenceRepository repository;

    @Test
    @DisplayName("멤버 알림 생성 (회원가입시)")
    void createTest(){

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .email(memberId)
                .sign(false)
                .build();

        Long id = adapter.save(memberNotification);

        repository.findByEmail(memberId).ifPresent(
                mn -> {
                    log.info("enter");
                    assertThat(mn.getEmail()).isEqualTo(memberId);
                    assertThat(mn.getSign()).isEqualTo(false);
                }
        );
    }
    
    @Test
    @DisplayName("멤버 알림 조회 (종 버튼에 빨간점이 있는지 없는지)")
    void getSignTest() {

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .email(memberId)
                .sign(false)
                .build();

        MemberNotification mn = repository.save(memberNotification);

        String failMemberId = "failEmail@gmail.com";

        Assertions.assertThat(adapter.getMemberNotification(memberId).getSign()).isEqualTo(mn.getSign());
        Assertions.assertThatThrownBy(() -> adapter.getMemberNotification(failMemberId)).isInstanceOf(BusinessException.class);
    }
}
package refrigerator.back.notification.outbound.adapter;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;
import refrigerator.back.notification.application.domain.MemberNotification;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataRedisTest
@Import(MemberNotificationAdapter.class)
@Slf4j
class MemberNotificationAdapterTest {

    @Autowired MemberNotificationAdapter adapter;

    @Autowired MemberNotificationPersistenceRepository repository;

    @BeforeEach
    void init(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("멤버 알림 생성 (회원가입시)")
    void createTest(){

        String memberId = "email123@gmail.com";

        String id = adapter.create(memberId);

        repository.findById(id).ifPresent(
                mn -> {
                    log.info("enter");
                    assertThat(mn.getMemberId()).isEqualTo(memberId);
                    assertThat(mn.getSign()).isEqualTo(false);
                }
        );
    }

    @Test
    @DisplayName("멤버 알림 변경 (신규 알림 생성(true) or 종 버튼 클릭 시(false))")
    void modifyTest() {

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(memberId)
                .sign(false)
                .build();

        repository.save(memberNotification);

        String id = adapter.modify(memberId, true);

        repository.findById(id).ifPresent(
                mn -> {
                    log.info("enter");
                    assertThat(mn.getMemberId()).isEqualTo(memberId);
                    assertThat(mn.getSign()).isEqualTo(true);
                }
        );
    }

    @Test
    @DisplayName("멤버 알림 변경 => 실패 테스트")
    void modifyFailTest() {

        String failMemberId = "failEmail@gmail.com";

        Assertions.assertThatThrownBy(() -> adapter.modify(failMemberId, true))
                .isInstanceOf(BusinessException.class);
    }
    
    @Test
    @DisplayName("멤버 알림 조회 (종 버튼에 빨간점이 있는지 없는지)")
    void getSignTest() {

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(memberId)
                .sign(false)
                .build();

        repository.save(memberNotification);

        String failMemberId = "failEmail@gmail.com";

        Assertions.assertThat(adapter.getSign(memberId)).isEqualTo(Optional.of(false));
        Assertions.assertThat(adapter.getSign(failMemberId)).isEqualTo(Optional.empty());
    }
}
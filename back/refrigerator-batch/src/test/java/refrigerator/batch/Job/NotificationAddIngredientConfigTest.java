package refrigerator.batch.Job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;
import refrigerator.back.member.application.domain.entity.Member;
import refrigerator.back.member.application.domain.value.MemberProfileImage;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "job.name=updateIngredientBatch_Job")
@Slf4j
class NotificationAddIngredientConfigTest extends BatchTestSupport {

    @Autowired
    MemberNotificationPersistenceRepository memberNotificationPersistenceRepository;

    @Autowired
    NotificationPersistenceRepository notificationPersistenceRepository;

    @AfterEach
    void after(){
        notificationPersistenceRepository.deleteAll();
        memberNotificationPersistenceRepository.deleteAll();
    }

    @Test
    @DisplayName("식재료 추가 알림 생성 테스트")
    void addNotificationByUpdatedIngredient() throws Exception {

        // given
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        String email = "email123@gmail.com";

        entityManager.getTransaction().begin();

        Member member = Member.builder()
                .email(email)
                .memberStatus(MemberStatus.STEADY_STATUS)
                .joinDateTime(now)
                .nickname("-")
                .password("****")
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .build();

        List<SuggestedIngredient> siList = new ArrayList<>();

        siList.add(SuggestedIngredient.builder().name("딜").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("딜").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("딜").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("딜").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("딜").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("로즈마리").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("로즈마리").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("로즈마리").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("로즈마리").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("로즈마리").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("g").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("개").email(email).build());
        siList.add(SuggestedIngredient.builder().name("관자").unit("개").email(email).build());

        save(member);

        for (SuggestedIngredient suggestedIngredient : siList) {
            save(suggestedIngredient);
        }

        MemberNotification memberNotification = MemberNotification.builder()
                .email(email)
                .sign(false)
                .build();

        MemberNotification mn = memberNotificationPersistenceRepository.save(memberNotification);

        entityManager.getTransaction().commit();

        assertThat(member.getId()).isNotNull();
        for (SuggestedIngredient suggestedIngredient : siList) {
            assertThat(suggestedIngredient.getId()).isNotNull();
        }

        //when
        JobParameters jobParameters = getUniqueParameterBuilder()
                .toJobParameters();

        JobExecution jobExecution = launchJob(jobParameters);

        //then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        Notification notification = notificationPersistenceRepository.findAll().stream().findAny().orElse(null);

        if(notification != null) {
            log.info("enter");
            log.info(notification.getMessage());
            log.info(notification.getPath());
            assertThat(notification.getMemberId()).isEqualTo(email);
            assertThat(notification.getType()).isEqualTo(NotificationType.INGREDIENT);
            assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
        }

        memberNotificationPersistenceRepository.findByEmail(email).ifPresent(
                memberNotification1 -> {
                    log.info("enter");
                    assertThat(memberNotification1.getId()).isEqualTo(mn.getId());
                    assertThat(memberNotification1.getSign()).isTrue();

                });

        entityManager.getTransaction().begin();

        delete(member);

        entityManager.flush();
        entityManager.clear();

        Query nativeQuery = entityManager.createNativeQuery("select * from registered_ingredient", RegisteredIngredient.class);

        List<RegisteredIngredient> resultList = nativeQuery.getResultList();

        for (RegisteredIngredient registeredIngredient : resultList) {
            log.info(registeredIngredient.toString());
        }

        entityManager.getTransaction().commit();
    }
}
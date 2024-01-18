package refrigerator.batch.Job;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.member.application.domain.entity.Member;
import refrigerator.back.member.application.domain.entity.QMember;
import refrigerator.back.member.application.domain.value.MemberProfileImage;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;
import refrigerator.batch.dto.OutIngredientGroupDTO;
import refrigerator.batch.dto.QOutIngredientGroupDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static refrigerator.back.ingredient.application.domain.entity.QIngredient.ingredient;

@SpringBootTest
@TestPropertySource(properties = "job.name=scheduleBatch_Job")
@Slf4j
class NotificationScheduleConfigTest extends BatchTestSupport{

    @Autowired
    MemberNotificationPersistenceRepository memberNotificationPersistenceRepository;

    @Autowired
    NotificationPersistenceRepository notificationPersistenceRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @AfterEach
    void after(){
        notificationPersistenceRepository.deleteAll();
        memberNotificationPersistenceRepository.deleteAll();
    }

    @Test
    @DisplayName("임박 식재료 알림 생성 테스트")
    void addNotificationByImminentIngredient() throws Exception {

        // given
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        LocalDate now2 = LocalDate.of(2023,1,1);

        String email = "email321@gmail.com";

        entityManager.getTransaction().begin();

        //////////////////회원/////////////////////

        Member member = Member.builder()
                .email(email)
                .memberStatus(MemberStatus.STEADY_STATUS)
                .joinDateTime(now)
                .nickname("-")
                .password("****")
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .build();

        save(member);

        //////////////////멤버 알림/////////////////////

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(email)
                .sign(false)
                .build();

        MemberNotification mn = memberNotificationPersistenceRepository.save(memberNotification);

        ///////////////등록된 식재료////////////////////////

        RegisteredIngredient.RegisteredIngredientBuilder builder1 = RegisteredIngredient.builder()
                .image(1)
                .unit("g");

        RegisteredIngredient registeredIngredient1 = builder1.name("감자").build();
        RegisteredIngredient registeredIngredient2 = builder1.name("고구마").build();

        save(registeredIngredient1);
        save(registeredIngredient2);

        ////////////////식재료///////////////////////

        List<Ingredient> ingredients = new ArrayList<>();

        Ingredient.IngredientBuilder builder2 = Ingredient.builder()
                .image(1)
                .email(email)
                .capacity(30.0)
                .capacityUnit("g")
                .deleted(false)
                .registrationDate(now2)
                .storageMethod(IngredientStorageType.FRIDGE);

        ingredients.add(builder2.name(registeredIngredient1.getName()).expirationDate(now2.plusDays(1)).build());
        ingredients.add(builder2.name(registeredIngredient2.getName()).expirationDate(now2.plusDays(1)).build());
        ingredients.add(builder2.name(registeredIngredient1.getName()).expirationDate(now2.plusDays(3)).build());

        save(ingredients.get(0));
        save(ingredients.get(1));
        save(ingredients.get(2));

        entityManager.getTransaction().commit();

        ///////////////////////////////////////

        // when
        JobParameters jobParameters = getUniqueParameterBuilder()
                .addString("createDate", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .toJobParameters();

        JobExecution jobExecution = launchJob(jobParameters);

        //then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        List<Notification> notificationList = notificationPersistenceRepository.findAll();

        assertThat(notificationList.size()).isEqualTo(2);

        OutIngredientGroupDTO ingredient1 = getOutIngredientDTO(now2, 1).orElse(null);

        if(ingredient1 != null) {
            assertThat(notificationList.get(0).getMemberId()).isEqualTo(ingredient1.getEmail());
            assertThat(notificationList.get(0).getMessage()).isEqualTo(ingredient1.getName() + " 외 " + (ingredient1.getCount() - 1) + "개 식재료의 소비기한이 " + 1 + "일 남았습니다. 식재료 확인하러가기!");
            assertThat(notificationList.get(0).getPath()).isEqualTo("/notification/exp?day=1");
            assertThat(notificationList.get(0).getType()).isEqualTo(NotificationType.ONE_DAY_BEFORE_EXPIRATION);
            assertThat(notificationList.get(0).getMethod()).isEqualTo(BasicHttpMethod.GET.name());
        }

        OutIngredientGroupDTO ingredient2 = getOutIngredientDTO(now2, 3).orElse(null);

        if(ingredient2 != null) {
            assertThat(notificationList.get(1).getMemberId()).isEqualTo(ingredient2.getEmail());
            assertThat(notificationList.get(1).getMessage()).isEqualTo(ingredient2.getName() + "의 소비기한이 " + 3 + "일 남았습니다. 식재료 확인하러가기!");
            assertThat(notificationList.get(1).getPath()).isEqualTo("/notification/exp?day=3");
            assertThat(notificationList.get(1).getType()).isEqualTo(NotificationType.THREE_DAY_BEFORE_EXPIRATION);
            assertThat(notificationList.get(1).getMethod()).isEqualTo(BasicHttpMethod.GET.name());
        }

        memberNotificationPersistenceRepository.findByMemberId(email).ifPresent(
                memberNotification1 -> {
                    log.info("enter");
                    assertThat(memberNotification1.getId()).isEqualTo(mn.getId());
                    assertThat(memberNotification1.getSign()).isTrue();
                });

        entityManager.getTransaction().begin();

        delete(member);

        delete(registeredIngredient1);
        delete(registeredIngredient2);

        for (Ingredient ingredient : ingredients) {
            delete(ingredient);
        }

        memberNotificationPersistenceRepository.deleteAll();

        entityManager.flush();
        entityManager.clear();

        entityManager.getTransaction().commit();
    }

    private Optional<OutIngredientGroupDTO> getOutIngredientDTO(LocalDate now, Integer days) {

        OutIngredientGroupDTO outIngredientDTO = jpaQueryFactory.select(new QOutIngredientGroupDTO(
                        ingredient.email,
                        ingredient.name.min(),
                        ingredient.id.count()))
                .from(ingredient)
                .where(
                        ingredient.expirationDate.eq(now.plusDays(days)),
                        QMember.member.memberStatus.eq(MemberStatus.STEADY_STATUS)
                )
                .leftJoin(QMember.member).on(QMember.member.email.eq(ingredient.email))
                .groupBy(ingredient.email)
                .fetchOne();

        return Optional.ofNullable(outIngredientDTO);
    }
}


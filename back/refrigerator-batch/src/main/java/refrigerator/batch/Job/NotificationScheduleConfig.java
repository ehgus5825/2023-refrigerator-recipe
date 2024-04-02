package refrigerator.batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.port.batch.DeleteNotificationBatchPort;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;
import refrigerator.batch.common.querydsl.expression.Expression;
import refrigerator.batch.common.querydsl.options.QuerydslNoOffsetStringOptions;
import refrigerator.batch.common.querydsl.reader.QuerydslNoOffsetPagingItemReader;
import refrigerator.batch.common.querydsl.reader.QuerydslPagingItemReader;
import refrigerator.batch.dto.OutIngredientGroupDTO;
import refrigerator.batch.dto.QOutIngredientGroupDTO;
import refrigerator.batch.jabParameter.CreateDateJobParameter;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static refrigerator.back.ingredient.application.domain.entity.QIngredient.ingredient;
import static refrigerator.back.member.application.domain.entity.QMember.member;

@RequiredArgsConstructor
@Configuration
@Slf4j
//@ConditionalOnProperty(name = "job.name", havingValue = "scheduleBatch_Job")
public class NotificationScheduleConfig {

    public static final String BATCH_NAME = "scheduleBatch";

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DeleteNotificationBatchPort deleteNotificationBatchPort;
    private final SaveMemberNotificationPort saveMemberNotificationPort;
    private final FindMemberNotificationPort findMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;

    private final CreateDateJobParameter jobParameter;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;

    @Bean(name = BATCH_NAME + "jobParameter")
    @JobScope
    public CreateDateJobParameter jobParameter() {
        return new CreateDateJobParameter();
    }

    /**
     * step 1 : 유통기한 안내 알림(매일), 일반 알림(14일 전) 삭제
     * step 2 : 유통기한 마감 1일전 식재료 알림 전송
     * step 3 : 유통기한 마감 3일전 식재료 알림 전송
     */

    @Bean
    public Job scheduleJob(){
        return jobBuilderFactory.get("scheduleJob")
                .preventRestart()
                .start(deleteNotificationStep())
                .next(createDeadlineNotificationByOneStep())
                .next(createDeadlineNotificationByThreeStep())
                .build();
    }

    /** step 1 **/

    @Bean
    @JobScope
    public Step deleteNotificationStep() {

        return stepBuilderFactory.get("deleteNotificationStep")
                .tasklet((contribution, chunkContext) -> {

                    deleteNotificationBatchPort.deleteNotification(true, currentTime.now());
                    deleteNotificationBatchPort.deleteNotification(false, currentTime.now().minusDays(14));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /** step 2 **/

    @Bean
    @JobScope
    public Step createDeadlineNotificationByOneStep() {
        return stepBuilderFactory.get("createDeadlineNotificationStep")
                .<OutIngredientGroupDTO, Notification> chunk(chunkSize)
                .reader(createDeadlineNotificationByOneReader())
                .processor(createDeadlineNotificationByOneProcessor())
                .writer(createDeadlineNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslPagingItemReader<OutIngredientGroupDTO> createDeadlineNotificationByOneReader() {

        LocalDate localDate = jobParameter.getCreateDate();

        QuerydslNoOffsetStringOptions<OutIngredientGroupDTO> options = new QuerydslNoOffsetStringOptions<>(ingredient.email, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(entityManagerFactory, chunkSize, options,
                queryFactory -> queryFactory
                        .select(new QOutIngredientGroupDTO(
                                ingredient.email,
                                ingredient.name.min(),
                                ingredient.id.count())
                        )
                        .from(ingredient)
                        .where(ingredient.expirationDate.eq(localDate.plusDays(1)),
                                member.memberStatus.eq(MemberStatus.STEADY_STATUS))
                        .leftJoin(member).on(member.email.eq(ingredient.email))
                        .groupBy(ingredient.email));
    }

    @Bean
    @StepScope
    public ItemProcessor<OutIngredientGroupDTO, Notification> createDeadlineNotificationByOneProcessor() {

        return (dto) -> {

            MemberNotification memberNotification = findMemberNotificationPort
                    .getMemberNotification(dto.getEmail());

            memberNotification.setSign(true);

            saveMemberNotificationPort.save(memberNotification);

            Notification notification = Notification.create(
                    NotificationType.ONE_DAY_BEFORE_EXPIRATION,
                    "/notification/exp?query=1",
                    dto.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now()
            );
            notification.createExpirationDateMessage(dto.getName(), dto.getCount(), 1);
            return notification;
        };
    }


    /** step 3 **/

    @Bean
    @JobScope
    public Step createDeadlineNotificationByThreeStep() {
        return stepBuilderFactory.get("createDeadlineNotificationStep")
                .<OutIngredientGroupDTO, Notification> chunk(chunkSize)
                .reader(createDeadlineNotificationByThreeReader())
                .processor(createDeadlineNotificationByThreeProcessor())
                .writer(createDeadlineNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslNoOffsetPagingItemReader<OutIngredientGroupDTO> createDeadlineNotificationByThreeReader() {

        LocalDate localDate = jobParameter.getCreateDate();

        QuerydslNoOffsetStringOptions<OutIngredientGroupDTO> options = new QuerydslNoOffsetStringOptions<>(ingredient.email, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(entityManagerFactory, chunkSize, options,
                queryFactory -> queryFactory
                        .select(new QOutIngredientGroupDTO(
                                ingredient.email,
                                ingredient.name.min(),
                                ingredient.id.count())
                        )
                        .from(ingredient)
                        .where(ingredient.expirationDate.eq(localDate.plusDays(3)),
                                member.memberStatus.eq(MemberStatus.STEADY_STATUS))
                        .leftJoin(member).on(member.email.eq(ingredient.email))
                        .groupBy(ingredient.email));
    }

    @Bean
    @StepScope
    public ItemProcessor<OutIngredientGroupDTO, Notification> createDeadlineNotificationByThreeProcessor() {

        return (dto) -> {

            MemberNotification memberNotification = findMemberNotificationPort
                    .getMemberNotification(dto.getEmail());

            memberNotification.setSign(true);

            saveMemberNotificationPort.save(memberNotification);

            Notification notification = Notification.create(
                    NotificationType.THREE_DAY_BEFORE_EXPIRATION,
                    "/notification/exp?query=3",
                    dto.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now()
            );
            notification.createExpirationDateMessage(dto.getName(), dto.getCount(), 3);
            return notification;
        };
    }

    /** step 2, 3 공용 writer **/

    @Bean
    @StepScope
    public JpaItemWriter<Notification> createDeadlineNotificationWriter() {
        JpaItemWriter<Notification> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}

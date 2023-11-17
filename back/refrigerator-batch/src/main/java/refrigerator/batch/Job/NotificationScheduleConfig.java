package refrigerator.batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.batch.DeleteNotificationBatchPort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;
import refrigerator.batch.common.querydsl.expression.Expression;
import refrigerator.batch.common.querydsl.options.QuerydslNoOffsetStringOptions;
import refrigerator.batch.common.querydsl.reader.QuerydslNoOffsetPagingItemReader;
import refrigerator.batch.common.querydsl.reader.QuerydslPagingItemReader;
import refrigerator.batch.dto.OutIngredientGroupDTO;
import refrigerator.batch.dto.QOutIngredientGroupDTO;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDate.from;
import static java.time.format.DateTimeFormatter.ofPattern;
import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.member.application.domain.QMember.member;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class NotificationScheduleConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DeleteNotificationBatchPort deleteNotificationBatchPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;

    @Bean
    public Job scheduleJob(){
        return jobBuilderFactory.get("scheduleJob")
                .preventRestart()
                .start(deleteNotificationStep())
                .next(createDeadlineNotificationByOneStep())
                .next(createDeadlineNotificationByThreeStep())
                .build();
    }

    // 알림 삭제 Step
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

    // 임박 식재료 알림 생성 Step (1일)
    @Bean
    @JobScope
    public Step createDeadlineNotificationByOneStep() {
        return stepBuilderFactory.get("createDeadlineNotificationStep")
                .<OutIngredientGroupDTO, Notification> chunk(chunkSize)
                .reader(createDeadlineNotificationByOneReader(null))
                .processor(createDeadlineNotificationByOneProcessor())
                .writer(createDeadlineNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslPagingItemReader<OutIngredientGroupDTO> createDeadlineNotificationByOneReader(
                                                                        @Value("#{jobParameters['date']}") String date) {

        LocalDate localDate = from(LocalDateTime.parse(date, ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));

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

            modifyMemberNotificationPort.modify(dto.getEmail(), true);

            Notification notification = Notification.create(
                    NotificationType.ONE_DAY_BEFORE_EXPIRATION,
                    "/notification/exp?day=1",
                    dto.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now()
            );
            notification.createExpirationDateMessage(dto.getName(), dto.getCount(), 1);
            return notification;
        };
    }

    // 임박 식재료 알림 생성 Step (3일)
    @Bean
    @JobScope
    public Step createDeadlineNotificationByThreeStep() {
        return stepBuilderFactory.get("createDeadlineNotificationStep")
                .<OutIngredientGroupDTO, Notification> chunk(chunkSize)
                .reader(createDeadlineNotificationByThreeReader(null))
                .processor(createDeadlineNotificationByThreeProcessor())
                .writer(createDeadlineNotificationWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslNoOffsetPagingItemReader<OutIngredientGroupDTO> createDeadlineNotificationByThreeReader(
                                                                        @Value("#{jobParameters['date']}") String date) {

        LocalDate localDate = from(LocalDateTime.parse(date, ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));

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
            modifyMemberNotificationPort.modify(dto.getEmail(), true);

            Notification notification = Notification.create(
                    NotificationType.THREE_DAY_BEFORE_EXPIRATION,
                    "/notification/exp?day=3",
                    dto.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now()
            );
            notification.createExpirationDateMessage(dto.getName(), dto.getCount() - 1, 3);
            return notification;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notification> createDeadlineNotificationWriter() {
        JpaItemWriter<Notification> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}

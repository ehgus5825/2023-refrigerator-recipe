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
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.batch.DeleteIngredientBatchPort;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;
import refrigerator.batch.common.querydsl.expression.Expression;
import refrigerator.batch.common.querydsl.options.QuerydslNoOffsetNumberOptions;
import refrigerator.batch.common.querydsl.reader.QuerydslNoOffsetPagingItemReader;

import javax.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

import static refrigerator.back.ingredient.application.domain.QSuggestedIngredient.suggestedIngredient;
import static refrigerator.back.member.application.domain.QMember.member;


@RequiredArgsConstructor
@Configuration
@Slf4j
public class NotificationAddIngredientConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DeleteIngredientBatchPort deleteIngredientBatchPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;


    @Bean
    public Job updateIngredientJob() {
        return jobBuilderFactory.get("updateIngredientJob")
                .preventRestart()
                .start(updateIngredientStep())
                .next(deleteSuggestedIngredientStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step deleteSuggestedIngredientStep(@Value("#{jobParameters['name']}") String name) {
        return stepBuilderFactory.get("deleteSuggestedIngredientStep")
                .tasklet((contribution, chunkContext) -> {
                    deleteIngredientBatchPort.deleteSuggestedIngredient(name);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step updateIngredientStep() {
        return stepBuilderFactory.get("updateIngredientStep")
                .<SuggestedIngredient, Notification>chunk(chunkSize)
                .reader(updateIngredientReader(null))
                .processor(updateIngredientProcessor(null,null))
                .writer(updateIngredientWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslNoOffsetPagingItemReader<SuggestedIngredient> updateIngredientReader(@Value("#{jobParameters['name']}") String name) {

        QuerydslNoOffsetNumberOptions<SuggestedIngredient, Long> option = new QuerydslNoOffsetNumberOptions<>(suggestedIngredient.id, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(entityManagerFactory, chunkSize, option,
                queryFactory -> queryFactory
                        .selectFrom(suggestedIngredient)
                        .where(
                                suggestedIngredient.name.eq(name),
                                member.memberStatus.eq(MemberStatus.STEADY_STATUS))
                        .leftJoin(member).on(member.email.eq(suggestedIngredient.email)));
    }

    @Bean
    @StepScope
    public ItemProcessor<SuggestedIngredient, Notification> updateIngredientProcessor(@Value("#{jobParameters['name']}") String name,
                                                                                      @Value("#{jobParameters['id']}") Long id) {

        return suggestedIngredient -> {

            modifyMemberNotificationPort.modify(suggestedIngredient.getEmail(), true);

            Notification notification = Notification.create(
                    NotificationType.INGREDIENT,
                    "/refrigerator/addUp/info?ingredient=" + name,
                    suggestedIngredient.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now()
            );
            notification.createIngredientMessage(name);
            return notification;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notification> updateIngredientWriter() {
        JpaItemWriter<Notification> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}

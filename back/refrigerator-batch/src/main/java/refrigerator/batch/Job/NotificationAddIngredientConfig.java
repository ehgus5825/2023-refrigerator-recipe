package refrigerator.batch.Job;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.batch.DeleteIngredientBatchPort;
import refrigerator.back.member.application.domain.value.MemberStatus;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;
import refrigerator.batch.common.querydsl.expression.Expression;
import refrigerator.batch.common.querydsl.options.QuerydslNoOffsetNumberOptions;
import refrigerator.batch.common.querydsl.reader.QuerydslNoOffsetPagingItemReader;
import refrigerator.batch.dto.SuggestedIngredientDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static refrigerator.back.ingredient.application.domain.entity.QSuggestedIngredient.suggestedIngredient;
import static refrigerator.back.ingredient.application.domain.entity.QRegisteredIngredient.registeredIngredient;
import static refrigerator.back.member.application.domain.entity.QMember.member;


@RequiredArgsConstructor
@Configuration
@Slf4j
@ConditionalOnProperty(name = "job.name", havingValue = "updateIngredientBatch_Job")
public class NotificationAddIngredientConfig {

    public static final String BATCH_NAME = "updateIngredientBatch";
    public static final String JOB_NAME = BATCH_NAME + "_Job";

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DeleteIngredientBatchPort deleteIngredientBatchPort;

    private final SaveMemberNotificationPort saveMemberNotificationPort;
    private final FindMemberNotificationPort findMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;

    private final DataSource dataSource;

    @Value("${chunkSize:1000}")
    private int chunkSize = 1000;

    /**
     * step 1 : 사용자가 제안했던 식재료를 추합해 가장 많은 단위를 채택하여 등록된 식재료에 저장.
     * step 2 : 사용자가 제안했던 식재료 등록 가능 알림 전송
     * step 3 : 쌓여있는 제안 식재료 삭제
     */

    @Bean(name = JOB_NAME)
    public Job updateIngredientJob() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(analyzeIngredientStep())
                .next(updateIngredientStep())
                .next(deleteSuggestedIngredientStep())
                .build();
    }

    /** step 1 **/

    @Bean
    @JobScope
    public Step analyzeIngredientStep() throws Exception {
        return stepBuilderFactory.get("analyzeIngredientStep")
                .<SuggestedIngredientDTO, RegisteredIngredient>chunk(chunkSize)
                .reader(analyzeIngredientReader())
                .processor(analyzeIngredientProcessor())
                .writer(analyzeIngredientWriter())
                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("t.name, t.unit");
        queryProvider.setFromClause("FROM (SELECT si.name, si.unit, ROW_NUMBER() OVER (PARTITION BY si.name ORDER BY COUNT(si.unit) DESC) as r " +
                "from suggested_ingredient si " +
                "LEFT JOIN `member` m ON m.email = si.email " +
                "WHERE m.status = 'STEADY_STATUS' " +
                "GROUP BY si.name, si.unit) as t ");
        queryProvider.setWhereClause("WHERE t.r = 1");
        queryProvider.setSortKey("t.name");

        return queryProvider.getObject();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<SuggestedIngredientDTO> analyzeIngredientReader() throws Exception {

        return new JdbcPagingItemReaderBuilder<SuggestedIngredientDTO>()
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(SuggestedIngredientDTO.class))
                .queryProvider(createQueryProvider())
                .name("updateIngredientReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<SuggestedIngredientDTO, RegisteredIngredient> analyzeIngredientProcessor() {
        return dto -> {

            log.info(dto.getName());
            log.info(dto.getUnit());

            return RegisteredIngredient.builder()
                    .name(dto.getName())
                    .unit(dto.getUnit())
                    .image(0)
                    .build();
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<RegisteredIngredient> analyzeIngredientWriter() {
        JpaItemWriter<RegisteredIngredient> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    /** Step 2 **/

    @Bean
    @JobScope
    public Step updateIngredientStep() {
        return stepBuilderFactory.get("updateIngredientStep")
                .<SuggestedIngredient, Notification>chunk(chunkSize)
                .reader(updateIngredientReader())
                .processor(updateIngredientProcessor())
                .writer(updateIngredientWriter())
                .build();
    }

    @Bean
    @StepScope
    public QuerydslNoOffsetPagingItemReader<SuggestedIngredient> updateIngredientReader() {

        QuerydslNoOffsetNumberOptions<SuggestedIngredient, Long> option = new QuerydslNoOffsetNumberOptions<>(suggestedIngredient.id, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(entityManagerFactory, chunkSize, option,
                queryFactory -> queryFactory
                        .selectFrom(suggestedIngredient)
                        .where(
                                member.memberStatus.eq(MemberStatus.STEADY_STATUS))
                        .leftJoin(member).on(member.email.eq(suggestedIngredient.email))
                        .leftJoin(registeredIngredient).on(registeredIngredient.name.eq(suggestedIngredient.name)));
    }

    @Bean
    @StepScope
    public ItemProcessor<SuggestedIngredient, Notification> updateIngredientProcessor() {

        return suggestedIngredient -> {

            MemberNotification memberNotification = findMemberNotificationPort
                    .getMemberNotification(suggestedIngredient.getEmail());

            memberNotification.setSign(true);

            saveMemberNotificationPort.save(memberNotification);

            Notification notification = Notification.create(
                    NotificationType.INGREDIENT,
                    "/refrigerator/addUp/info?ingredient=" + suggestedIngredient.getName(),
                    suggestedIngredient.getEmail(),
                    BasicHttpMethod.GET.name(),
                    currentTime.now()
            );
            notification.createIngredientMessage(suggestedIngredient.getName());
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

    /** step 3 **/

    @Bean
    @JobScope
    public Step deleteSuggestedIngredientStep() {
        return stepBuilderFactory.get("deleteSuggestedIngredientStep")
                .tasklet((contribution, chunkContext) -> {
                    Long aLong = deleteIngredientBatchPort.deleteSuggestedIngredient();
                    log.info("along : {}", aLong);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}

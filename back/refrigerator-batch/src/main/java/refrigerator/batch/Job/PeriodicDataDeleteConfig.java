package refrigerator.batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import refrigerator.back.comment.application.port.batch.DeleteCommentBatchPort;
//import refrigerator.back.ingredient.application.port.batch.DeleteIngredientBatchPort;
//import refrigerator.back.mybookmark.application.port.batch.DeleteBookmarkBatchPort;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class PeriodicDataDeleteConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    private final DeleteIngredientBatchPort deleteIngredientBatchPort;
//    private final DeleteCommentBatchPort deleteCommentBatchPort;
//    private final DeleteBookmarkBatchPort deleteBookmarkBatchPort;

    @Bean
    public Job periodicDeleteScheduleJob() {
        return jobBuilderFactory.get("periodicDeleteScheduleJob")
                .preventRestart()
                .start(deleteCommentStep())
                .next(deleteIngredientStep())
                .next(deleteBookmarkStep())
                .build();
    }

    @Bean
    @JobScope
    public Step deleteCommentStep() {
        return stepBuilderFactory.get("deleteCommentStep")
                .tasklet((contribution, chunkContext) -> {

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step deleteIngredientStep() {
        return stepBuilderFactory.get("deleteIngredientStep")
                .tasklet((contribution, chunkContext) -> {
//                    deleteIngredientBatchPort.deleteIngredients();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step deleteBookmarkStep() {
        return stepBuilderFactory.get("deleteBookmarkStep")
                .tasklet((contribution, chunkContext) -> {

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}

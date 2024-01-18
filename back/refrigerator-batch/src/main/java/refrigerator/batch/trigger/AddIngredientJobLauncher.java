package refrigerator.batch.trigger;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import refrigerator.back.global.time.CurrentTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AddIngredientJobLauncher {

    private final Job updateIngredientJob;
    private final JobLauncher jobLauncher;
    private final CurrentTime<LocalDateTime> currentTime;

    /**
     * 1. 사용자 요청애 따른 식재료 등록
     * 2. 사용자에게 식재료 등록 가능 알림 전송
     * 3. 요청 식재료 삭제
     * (매 달 1일, 15일 마다 실행)
     */
    @Scheduled(cron = "0 0 0 1,15 * *")
    public void addIngredientJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        jobParameterMap.put("createDate", new JobParameter(currentTime.now().format(format)));

        JobParameters parameters = new JobParameters(jobParameterMap);

        JobExecution jobExecution = jobLauncher.run(updateIngredientJob, parameters);
    }
}
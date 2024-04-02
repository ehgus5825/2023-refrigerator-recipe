package refrigerator.batch.trigger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.batch.Job.NotificationScheduleConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobScheduler {

    @Autowired
    private NotificationScheduleConfig notificationScheduleConfig;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private CurrentTime<LocalDateTime> currentTime;

    /**
     * 알림 삭제, 유통기한 임박 알림 전송
     * (매 자정마다 실행)
     * **/
//    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "0 0/5 * * * *")
    public void jobScheduled() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        jobParameterMap.put("createDate", new JobParameter(currentTime.now().format(format)));

        JobParameters parameters = new JobParameters(jobParameterMap);

        JobExecution jobExecution = jobLauncher.run(notificationScheduleConfig.scheduleJob(), parameters);
    }
}

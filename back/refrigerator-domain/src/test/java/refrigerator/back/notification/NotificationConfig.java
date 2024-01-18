package refrigerator.back.notification;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.global.time.TimeService;
import refrigerator.back.notification.application.service.NotificationTimeService;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapper;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapperImpl;

import java.time.LocalDateTime;

@TestConfiguration
public class NotificationConfig {

    @Bean
    public OutNotificationMapper outNotificationMapper(){
        return new OutNotificationMapperImpl();
    }

    @Bean
    public NotificationTimeService notificationTimeService(){
        return new TimeService(LocalDateTime::now);
    }
}

package refrigerator.back.notification.outbound.mapper;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OutNotificationMapperConfig {

    @Bean
    public OutNotificationMapper outNotificationMapper(){
        return new OutNotificationMapperImpl();
    }
}

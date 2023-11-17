package refrigerator.back.global.time.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.global.time.CurrentTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class CurrentTimeConfig {

    @Bean
    public CurrentTime<LocalDateTime> localDateTimeCurrentTime(){
        return LocalDateTime::now;
    }

    @Bean
    public CurrentTime<LocalDate> localDateCurrentTime(){
        return LocalDate::now;
    }

}

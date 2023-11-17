package refrigerator.back.global.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.global.common.RandomUUID;

import java.util.UUID;

@Configuration
public class RandomUUIDConfig {

    @Bean
    public RandomUUID randomUUID(){
        return () -> UUID.randomUUID().toString();
    }
}

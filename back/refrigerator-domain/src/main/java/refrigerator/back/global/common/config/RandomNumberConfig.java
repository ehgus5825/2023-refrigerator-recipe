package refrigerator.back.global.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import refrigerator.back.global.common.RandomNumber;

import java.util.Random;

@Configuration
public class RandomNumberConfig {

//    @Bean
//    public int intRandomNumber(){
//        return new Random().nextInt(5);
//    }

    @Bean
    public RandomNumber<Integer> intRandomNumber(){
        return () -> new Random().nextInt(5);
    }
}

package refrigerator.back.ingredient.outbound.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapperImpl;

@TestConfiguration
public class OutIngredientMapperConfig {

    @Bean
    public OutIngredientMapper outIngredientMapper(){
        return new OutIngredientMapperImpl();
    }
}

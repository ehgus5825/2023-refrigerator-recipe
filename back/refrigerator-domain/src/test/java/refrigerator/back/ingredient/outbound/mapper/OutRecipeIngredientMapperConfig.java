package refrigerator.back.ingredient.outbound.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OutRecipeIngredientMapperConfig {

    @Bean
    public OutRecipeIngredientMapper outRecipeIngredientMapper(){
        return new OutRecipeIngredientMapperImpl();
    }
}

package refrigerator.back.ingredient;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapperImpl;
import refrigerator.back.ingredient.outbound.mapper.OutSubIngredientMapper;
import refrigerator.back.ingredient.outbound.mapper.OutSubIngredientMapperImpl;

@TestConfiguration
public class IngredientConfig {

    @Bean
    public OutSubIngredientMapper outRecipeIngredientMapper(){
        return new OutSubIngredientMapperImpl();
    }

    @Bean
    public OutIngredientMapper outIngredientMapper(){
        return new OutIngredientMapperImpl();
    }
}

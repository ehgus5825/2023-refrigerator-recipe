package refrigerator.back.recipe.outbound.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, RecipeUpdateQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeUpdateQueryRepositoryTest {

    @Autowired
    RecipeUpdateQueryRepository queryRepository;

    @Test
    @DisplayName("value에 따른 조건절 확인하기")
    void decideUpdateCondition(){
        assertTrue(queryRepository.decideUpdateCondition(-1).toString().contains("1"));
    }
}
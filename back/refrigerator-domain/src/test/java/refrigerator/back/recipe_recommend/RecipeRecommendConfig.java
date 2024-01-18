package refrigerator.back.recipe_recommend;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.global.s3.ImageUrlConvert;

@TestConfiguration
public class RecipeRecommendConfig {

    @Bean
    public ImageUrlConvert imageUrlConvert(){
        return new ImageUrlConvert() {
            @Override
            public String getUrl(String fileName) {
                return "s3/route/" + fileName;
            }
        };
    }
}

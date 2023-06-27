package refrigerator.back.recipe_search.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import java.lang.reflect.Field;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeSearchDto extends Image {
    private Long recipeID;
    private String recipeName;
    private String image;
    private Double scoreAvg;
    private Integer views;

    public boolean checkNotNull(){
        try{
            for (Field field : getClass().getDeclaredFields()){
                if (field.get(this) == null){
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
package refrigerator.back.recipe.application.dto;

import lombok.*;

@Getter
@Builder
public class RecipeIngredientDto {

    private Long ingredientId;
    private String name;
    private String volume;
    private String transUnit;
    private String transVolume;
    private String type;
    private Boolean isOwned;

    public void match(MyIngredientDto dto){
        if (dto != null){
            if (name.equals(dto.getName()) &&
                    dto.getVolume() >= Double.parseDouble(transVolume) && transUnit.equals(dto.getUnit())){
                isOwned = true;
                return ;
            }
        }
        isOwned = false;
    }
}

package refrigerator.server.api.recipe_search.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class InRecipeBasicListDto<T> implements Serializable {
    List<T> data;
    public InRecipeBasicListDto(List<T> data) {
        this.data = data;
    }
}

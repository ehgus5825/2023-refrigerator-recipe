package refrigerator.back.recipe.adapter.dto;

import refrigerator.back.global.common.BasicListResponseDTO;

import java.util.List;

public class RecipeCourseDtoList extends BasicListResponseDTO<RecipeCourseDTO> {
    public RecipeCourseDtoList(List<RecipeCourseDTO> data) {
        super(data);
    }
}

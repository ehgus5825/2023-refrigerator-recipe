package refrigerator.back.ingredient.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RecipeIngredientCollectionTest {

    @Test
    @DisplayName("빈 리스트 체크")
    void validationEmptyTest(){
        assertThatThrownBy(() -> new RecipeIngredientCollection(new ArrayList<>()))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("id 리스트 반환 확인 테스트")
    void getIdsTest(){

        RecipeIngredientDto.RecipeIngredientDtoBuilder builder1 = RecipeIngredientDto.builder();

        List<RecipeIngredientDto> recipeIngredients = new ArrayList<>();

        recipeIngredients.add(builder1.ingredientId(1L).name("감자").build());
        recipeIngredients.add(builder1.ingredientId(2L).name("호박").build());
        recipeIngredients.add(builder1.ingredientId(3L).name("시금치").build());
        recipeIngredients.add(builder1.ingredientId(4L).name("수박").build());

        MyIngredientDTO.MyIngredientDTOBuilder builder2 = MyIngredientDTO.builder()
                .volume(30.0);

        List<MyIngredientDTO> myIngredients = new ArrayList<>();

        myIngredients.add(builder2.name("감자").unit("g").build());
        myIngredients.add(builder2.name("고구마").unit("g").build());
        myIngredients.add(builder2.name("호박").unit("개").build());
        myIngredients.add(builder2.name("피클").unit("개").build());

        RecipeIngredientCollection collection = new RecipeIngredientCollection(recipeIngredients);
        List<Long> ids = collection.getIds(myIngredients);

        assertThat(ids.size()).isEqualTo(2);
        assertThat(ids.get(0)).isEqualTo(1L);
        assertThat(ids.get(1)).isEqualTo(2L);
    }
}
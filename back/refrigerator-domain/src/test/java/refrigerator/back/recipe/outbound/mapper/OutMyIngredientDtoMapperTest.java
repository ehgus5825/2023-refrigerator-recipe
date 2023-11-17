package refrigerator.back.recipe.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.dto.MyIngredientDto;
import refrigerator.back.recipe.outbound.dto.OutMyIngredientDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OutMyIngredientDtoMapperTest {

    OutMyIngredientDtoMapper mapper = Mappers.getMapper(OutMyIngredientDtoMapper.class);

    @Test
    @DisplayName("OutMyIngredientDto -> MyIngredientDto 매핑 테스트")
    void toMyIngredientDto() {
        // given
        OutMyIngredientDto outDto = new OutMyIngredientDto("name", 50.0, "g");
        // when
        MyIngredientDto result = mapper.toMyIngredientDto(outDto);
        // then
        assertNotNull(result);
        assertNotEquals(MyIngredientDto.builder().build(), result);
    }

    @Test
    @DisplayName("MyIngredientTable 매핑 테스트")
    void makeMyIngredientTable(){
        // given
        List<OutMyIngredientDto> myIngredients = Arrays.asList(
                new OutMyIngredientDto("사과", 50.0, "g"),
                new OutMyIngredientDto("배", 50.0, "g"));
        // when
        OutMyIngredientDtoMappingCollection collection = new OutMyIngredientDtoMappingCollection(myIngredients);
        Map<String, MyIngredientDto> table = collection.makeMyIngredientTable(mapper);
        // then
        assertNotNull(table.getOrDefault("사과", null));
        assertNotNull(table.getOrDefault("배", null));
        assertNull(table.getOrDefault("참외", null));
    }
}
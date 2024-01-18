package refrigerator.back.ingredient.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.port.out.UpdateVolumeIngredientPort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Import(UpdateVolumeIngredientPort.class)
class MyIngredientCollectionTest {

    @Autowired UpdateVolumeIngredientPort updateVolumeIngredientPort;

    @Test
    @DisplayName("빈 리스트 테스트")
    void validationEmptyTest(){
        assertThatThrownBy(() -> new MyIngredientCollection(new ArrayList<>(), new ArrayList<>()))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("두 리스트 이름 & 단위가 모두 같은지 확인")
    void validationNamesAndUnitTest(){

        MyIngredientDTO.MyIngredientDTOBuilder builder1 = MyIngredientDTO.builder()
                .volume(30.0);

        IngredientDeductionDTO.IngredientDeductionDTOBuilder builder2 = IngredientDeductionDTO.builder()
                .volume(30.0);

        List<MyIngredientDTO> myIngredients = new ArrayList<>();

        myIngredients.add(builder1.name("감자").unit("g").build());
        myIngredients.add(builder1.name("고구마").unit("g").build());
        myIngredients.add(builder1.name("호박").unit("개").build());
        myIngredients.add(builder1.name("피클").unit("개").build());

        List<IngredientDeductionDTO> dtos = new ArrayList<>();

        dtos.add(builder2.name("김치").unit("개").build());
        dtos.add(builder2.name("고구마").unit("g").build());
        dtos.add(builder2.name("호박").unit("개").build());
        dtos.add(builder2.name("피클").unit("개").build());


        assertThatThrownBy(() -> new MyIngredientCollection(myIngredients, dtos))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("식재료 차감 예외 확인 => 용량 초과")
    void deductionTest1(){

        MyIngredientDTO.MyIngredientDTOBuilder builder1 = MyIngredientDTO.builder()
                .volume(30.0);

        IngredientDeductionDTO.IngredientDeductionDTOBuilder builder2 = IngredientDeductionDTO.builder()
                .volume(10000.0);

        List<MyIngredientDTO> myIngredients = new ArrayList<>();

        myIngredients.add(builder1.name("감자").unit("g").build());
        myIngredients.add(builder1.name("고구마").unit("g").build());
        myIngredients.add(builder1.name("호박").unit("개").build());
        myIngredients.add(builder1.name("피클").unit("개").build());

        List<IngredientDeductionDTO> dtos = new ArrayList<>();

        dtos.add(builder2.name("감자").unit("g").build());
        dtos.add(builder2.name("고구마").unit("g").build());
        dtos.add(builder2.name("호박").unit("개").build());
        dtos.add(builder2.name("피클").unit("개").build());


        MyIngredientCollection collection = new MyIngredientCollection(myIngredients, dtos);
        assertThatThrownBy(() -> collection.deduction(updateVolumeIngredientPort))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("식재료 차감 예외 확인 => 용량 0")
    void deductionTest2(){

        MyIngredientDTO.MyIngredientDTOBuilder builder1 = MyIngredientDTO.builder()
                .volume(30.0);

        IngredientDeductionDTO.IngredientDeductionDTOBuilder builder2 = IngredientDeductionDTO.builder()
                .volume(0.0);

        List<MyIngredientDTO> myIngredients = new ArrayList<>();

        myIngredients.add(builder1.name("감자").unit("g").build());
        myIngredients.add(builder1.name("고구마").unit("g").build());
        myIngredients.add(builder1.name("호박").unit("개").build());
        myIngredients.add(builder1.name("피클").unit("개").build());

        List<IngredientDeductionDTO> dtos = new ArrayList<>();

        dtos.add(builder2.name("감자").unit("g").build());
        dtos.add(builder2.name("고구마").unit("g").build());
        dtos.add(builder2.name("호박").unit("개").build());
        dtos.add(builder2.name("피클").unit("개").build());

        MyIngredientCollection collection = new MyIngredientCollection(myIngredients, dtos);
        assertThatThrownBy(() -> collection.deduction(updateVolumeIngredientPort))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("식재료 차감 예외 확인 => 용량 null")
    void deductionTest3(){

        MyIngredientDTO.MyIngredientDTOBuilder builder1 = MyIngredientDTO.builder();

        IngredientDeductionDTO.IngredientDeductionDTOBuilder builder2 = IngredientDeductionDTO.builder()
                .volume(0.0);

        List<MyIngredientDTO> myIngredients = new ArrayList<>();

        myIngredients.add(builder1.name("감자").unit("g").build());
        myIngredients.add(builder1.name("고구마").unit("g").build());
        myIngredients.add(builder1.name("호박").unit("개").build());
        myIngredients.add(builder1.name("피클").unit("개").build());

        List<IngredientDeductionDTO> dtos = new ArrayList<>();

        dtos.add(builder2.name("감자").unit("g").build());
        dtos.add(builder2.name("고구마").unit("g").build());
        dtos.add(builder2.name("호박").unit("개").build());
        dtos.add(builder2.name("피클").unit("개").build());

        MyIngredientCollection collection = new MyIngredientCollection(myIngredients, dtos);
        assertThatThrownBy(() -> collection.deduction(updateVolumeIngredientPort))
                .isInstanceOf(BusinessException.class);
    }
}
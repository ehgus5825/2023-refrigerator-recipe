package refrigerator.back.ingredient.application.domain;

import lombok.extern.slf4j.Slf4j;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.port.out.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.UpdateVolumeIngredientPort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Slf4j
public class MyIngredientCollection {

    List<MyIngredientDTO> myIngredients;
    List<IngredientDeductionDTO> ingredientDTOList;

    public MyIngredientCollection(List<MyIngredientDTO> myIngredients, List<IngredientDeductionDTO> ingredientDTOList) {
        this.myIngredients = myIngredients;
        this.ingredientDTOList = ingredientDTOList;

        validationEmpty();
        validationNamesAndUnit();
    }

    private void validationEmpty(){
        if (myIngredients.size() == 0)
            throw new BusinessException(EMPTY_INGREDIENT_LIST);
    }

    private void validationNamesAndUnit(){
        Map<String, String> myIngredients = this.myIngredients.stream().collect(Collectors
                .toMap(MyIngredientDTO::getName, MyIngredientDTO::getUnit));

        Map<String, String> dtos = ingredientDTOList.stream().collect(Collectors
                .toMap(IngredientDeductionDTO::getName, IngredientDeductionDTO::getUnit));

        for (String name : dtos.keySet()) {
            if(myIngredients.get(name) == null || !myIngredients.get(name).equals(dtos.get(name)) )
                throw new BusinessException(ERROR_INGREDIENT_MATCHING);
        }
    }

    public void deduction(UpdateVolumeIngredientPort updateVolumeIngredientPort, DeleteIngredientPort deleteIngredientPort){
        Map<String, Double> ingredientDTOMap = ingredientDTOList.stream().collect(Collectors
                .toMap(IngredientDeductionDTO::getName, IngredientDeductionDTO::getVolume));

        myIngredients.forEach(ingredient -> {

            Double volume = ingredientDTOMap.get(ingredient.getName());

            if(volume != null) {
                if (volume > 9999.9 || volume <= 0.0) {
                    throw new BusinessException(EXCEEDED_CAPACITY_RANGE);
                }

                if(ingredient.getVolume() > volume){
                    Double updatedVolume = ingredient.getVolume() - volume;
                    updateVolumeIngredientPort.updateVolume(ingredient.getId(), updatedVolume);
                }
                else {
                    updateVolumeIngredientPort.updateVolume(ingredient.getId(), 0.0);
                    deleteIngredientPort.deleteIngredient(ingredient.getId());
                }
            }
        });
    }
}

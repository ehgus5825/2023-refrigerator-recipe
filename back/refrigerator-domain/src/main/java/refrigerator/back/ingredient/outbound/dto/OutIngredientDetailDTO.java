package refrigerator.back.ingredient.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;

import java.time.LocalDate;

@Getter
@Builder
public class OutIngredientDetailDTO {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private LocalDate registrationDate;
    private Double volume;
    private String unit;
    private IngredientStorageType storage;
    private String imageName;


    @QueryProjection
    public OutIngredientDetailDTO(Long ingredientID, String name, LocalDate expirationDate, LocalDate registrationDate,
                                  Double volume, String unit, IngredientStorageType storage, String image) {

        this.ingredientID = ingredientID;
        this.name = name;
        this.expirationDate = expirationDate;
        this.registrationDate = registrationDate;
        this.volume = volume;
        this.unit = unit;
        this.storage = storage;
        this.imageName = image;
    }

    public IngredientDetailDTO mapping(OutIngredientMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toIngredientDetailDto(this, imageUrlConvert.getUrl(this.imageName));
    }

}

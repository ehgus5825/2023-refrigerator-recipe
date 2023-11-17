package refrigerator.back.ingredient.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import refrigerator.back.global.exception.MappingException;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.exception.MemberExceptionType;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class OutIngredientDTO {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private String imageName;

    @QueryProjection
    public OutIngredientDTO(Long ingredientID, String name, LocalDate expirationDate, String image) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.expirationDate = expirationDate;
        this.imageName = image;
    }

    public IngredientDTO mapping(OutIngredientMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toIngredientDto(this, imageUrlConvert.getUrl(this.imageName));
    }
}

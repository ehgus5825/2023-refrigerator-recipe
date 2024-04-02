package refrigerator.back.myscore.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.dto.MyScorePreviewDto;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDetailDto;
import refrigerator.back.myscore.outbound.dto.OutMyScorePreviewDto;

@Mapper(componentModel = "spring")
public interface OutMyScoreListDtoMapper {

    OutMyScoreListDtoMapper INSTANCE = Mappers.getMapper(OutMyScoreListDtoMapper.class);

    @Mapping(source = "dto.recipeImageName", target = "recipeImage")
    MyScoreDetailDto toMyScoreDetailDto(OutMyScoreDetailDto dto);

    @Mapping(source = "dto.recipeImageName", target = "recipeImage")
    MyScorePreviewDto toMyScorePreviewDto(OutMyScorePreviewDto dto);

}

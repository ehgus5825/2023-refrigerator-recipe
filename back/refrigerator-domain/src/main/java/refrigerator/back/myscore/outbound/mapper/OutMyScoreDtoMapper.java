package refrigerator.back.myscore.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.application.dto.MyScoreDto;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDto;

@Mapper(componentModel = "spring")
public interface OutMyScoreDtoMapper {

    OutMyScoreDtoMapper INSTANCE = Mappers.getMapper(OutMyScoreDtoMapper.class);

    MyScoreDto toMyScoreDto(OutMyScoreDto outMyScoreDto);

}

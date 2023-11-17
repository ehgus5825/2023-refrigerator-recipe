package refrigerator.back.mybookmark.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkPreviewDto;

@Mapper(componentModel = "spring")
public interface OutMyBookmarkDtoMapper {

    OutMyBookmarkDtoMapper INSTANCE = Mappers.getMapper(OutMyBookmarkDtoMapper.class);

    MyBookmarkDto toMyBookmarkDto(OutMyBookmarkDto dto, String recipeImage);
    MyBookmarkPreviewDto toMyBookmarkPreviewDto(OutMyBookmarkPreviewDto dto, String recipeImage);

}

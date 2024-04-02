package refrigerator.back.mybookmark.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkPreviewDto;

@Mapper(componentModel = "spring")
public interface OutMyBookmarkDtoMapper {

    OutMyBookmarkDtoMapper INSTANCE = Mappers.getMapper(OutMyBookmarkDtoMapper.class);

    @Mapping(source = "dto.recipeImageName", target = "recipeImage")
    MyBookmarkDto toMyBookmarkDto(OutMyBookmarkDto dto);

    @Mapping(source = "dto.recipeImageName", target = "recipeImage")
    MyBookmarkPreviewDto toMyBookmarkPreviewDto(OutMyBookmarkPreviewDto dto);

}

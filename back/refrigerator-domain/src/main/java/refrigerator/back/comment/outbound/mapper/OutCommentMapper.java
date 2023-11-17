package refrigerator.back.comment.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.outbound.dto.OutCommentDto;
import refrigerator.back.comment.application.dto.CommentDto;


@Mapper(componentModel = "spring")
public interface OutCommentMapper {

    OutCommentMapper INSTANCE = Mappers.getMapper(OutCommentMapper.class);

    @Mappings({
            @Mapping(target = "likedInfo", ignore = true),
            @Mapping(target = "createDate", source = "createDate")
    })
    CommentDto toCommentDto(OutCommentDto dto, String createDate);

}

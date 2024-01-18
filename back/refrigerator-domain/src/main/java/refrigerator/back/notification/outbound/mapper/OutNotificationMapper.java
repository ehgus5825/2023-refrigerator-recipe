package refrigerator.back.notification.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.outbound.dto.OutCommentNotificationDTO;
import refrigerator.back.notification.outbound.dto.OutNotificationDTO;


@Mapper(componentModel = "spring")
public interface OutNotificationMapper {

    OutNotificationMapper INSTANCE = Mappers.getMapper(OutNotificationMapper.class);

    CommentNotificationDTO toCommentNotificationDetail(OutCommentNotificationDTO dto);

    @Mapping(source = "date", target = "registerTime")
    NotificationDTO toNotificationDTO(OutNotificationDTO dto, String date);
}

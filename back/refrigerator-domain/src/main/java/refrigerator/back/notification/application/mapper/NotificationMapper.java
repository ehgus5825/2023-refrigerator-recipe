package refrigerator.back.notification.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.dto.NotificationDTO;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "date", target = "registerTime")
    NotificationDTO toNotificationDTO(Notification notification, String date);
}

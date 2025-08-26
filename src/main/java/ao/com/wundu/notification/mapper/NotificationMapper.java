package ao.com.wundu.notification.mapper;

import ao.com.wundu.notification.entity.Notification;
import ao.com.wundu.notification.dtos.NotificationResponse;

public class NotificationMapper {
    public static NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
            n.getId(),
            n.getUser().getId(),
            n.getCategory().getId(),
            n.getTitle(),
            n.getMessage(),
            n.getIsRead(),
            n.getCreatedAt()
        );
    }
}

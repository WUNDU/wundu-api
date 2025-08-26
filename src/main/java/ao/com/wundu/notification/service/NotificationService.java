package ao.com.wundu.notification.service;

import ao.com.wundu.notification.dtos.NotificationResponse;
import java.util.List;

public interface NotificationService {
    void notifyIfLimitExceeded(String userId, String categoryId, double totalSpent, double monthlyLimit);
    List<NotificationResponse> getUnreadNotifications(String userId);
}

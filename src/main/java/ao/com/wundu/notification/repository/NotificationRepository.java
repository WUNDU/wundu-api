package ao.com.wundu.notification.repository;

import ao.com.wundu.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByUserIdAndIsReadFalse(String userId);
    boolean existsByUserIdAndCategoryIdAndIsReadFalse(String userId, String categoryId);
}

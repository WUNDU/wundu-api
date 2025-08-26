package ao.com.wundu.notification.service.impl;

import ao.com.wundu.notification.entity.Notification;
import ao.com.wundu.notification.dtos.NotificationResponse;
import ao.com.wundu.notification.mapper.NotificationMapper;
import ao.com.wundu.notification.repository.NotificationRepository;
import ao.com.wundu.notification.service.NotificationService;
import ao.com.wundu.exception.ResourceNotFoundException;
import ao.com.wundu.usuario.repository.UserRepository;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.category.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void notifyIfLimitExceeded(String userId, String categoryId, double totalSpent, double monthlyLimit) {
        if (totalSpent <= monthlyLimit) return;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id=" + userId + " não encontrado"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com id=" + categoryId + " não encontrada"));

        boolean alreadyNotified = notificationRepository.existsByUserIdAndCategoryIdAndIsReadFalse(userId, categoryId);
        if (alreadyNotified) return;

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setCategory(category);
        notification.setTitle("Gasto elevado em " + category.getName());
        notification.setMessage(String.format(
                "Você já gastou R$ %.2f na categoria %s, ultrapassando o limite de R$ %.2f.",
                totalSpent, category.getName(), monthlyLimit
        ));
        notification.setIsRead(false);

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getUnreadNotifications(String userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId).stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }
}

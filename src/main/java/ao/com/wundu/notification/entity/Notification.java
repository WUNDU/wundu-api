package ao.com.wundu.notification.entity;

import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.category.entity.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "notifications",
    indexes = {
        @Index(name = "idx_notification_user_id", columnList = "user_id"),
        @Index(name = "idx_notification_is_read", columnList = "is_read")
    }
)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    public Notification() {}

    public Notification(User user, Category category, String title, String message, Boolean isRead) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
    }

    public Notification(String id, User user, Category category, String title, String message, Boolean isRead) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.title = title;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}

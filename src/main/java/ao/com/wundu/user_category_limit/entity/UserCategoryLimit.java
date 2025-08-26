package ao.com.wundu.user_category_limit.entity;

import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.category.entity.Category;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_category_limit",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "category_id"}))
public class UserCategoryLimit {

    @Id
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "monthly_limit", nullable = false)
    private Double monthlyLimit = -1.0;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Double getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(Double monthlyLimit) { this.monthlyLimit = monthlyLimit; }
}

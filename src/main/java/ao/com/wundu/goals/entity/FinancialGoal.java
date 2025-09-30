package ao.com.wundu.goals.entity;

import ao.com.wundu.goals.enums.GoalStatus;
import ao.com.wundu.goals.enums.GoalType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_goals")
public class FinancialGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private GoalType type;

    @Column(name = "target_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "current_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private GoalStatus status = GoalStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<GoalProgress> progress = new ArrayList<>();

    public FinancialGoal() {}

    // convenience methods
    public void addProgress(GoalProgress p) {
        this.progress.add(p);
        p.setGoal(this);
    }

    public void increaseCurrentAmount(java.math.BigDecimal amount) {
        if (amount != null) {
            this.currentAmount = this.currentAmount.add(amount);
        }
    }

    // ... getters and setters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public GoalType getType() { return type; }
    public void setType(GoalType type) { this.type = type; }
    public java.math.BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(java.math.BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public java.math.BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(java.math.BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<GoalProgress> getProgress() { return progress; }
    public void setProgress(List<GoalProgress> progress) { this.progress = progress; }
}

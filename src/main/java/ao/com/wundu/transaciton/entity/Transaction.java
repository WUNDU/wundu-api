package ao.com.wundu.transaciton.entity;

import ao.com.wundu.category.entity.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String type;

    private String source;

    @Column(nullable = false)
    private Double amount;

    private String userId;

    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Transaction() {}

    public Transaction(String id, Double amount, String source, String description, String type, LocalDateTime dateTime, LocalDate transactionDate, String userId) {
        this.id = id;
        this.amount = amount;
        this.source = source;
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
        this.transactionDate = transactionDate;
        this.userId = userId;
    }

    public Transaction(Double amount, String description, String type, String userId, LocalDate transactionDate) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.dateTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public Double getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }
    
    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}

package ao.com.wundu.infra.presentation.entities;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import jakarta.persistence.*;

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

    @CreationTimestamp
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    public Transaction() {}

    public Transaction(String id, Double amount, String source, String description, String type, LocalDateTime dateTime, String userId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
        this.userId = userId;
        this.source = source;
    }

    public Transaction(Double amount, String description, String type, String userId) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.userId = userId;
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
    
    public String getDescription() { 
        return description; 
    }
    
    public LocalDateTime getDateTime() { 
        return dateTime; 
    }
    
    public void setType(String type) { 
        this.type = type; 
    }
    
    public void setAmount(Double amount) { 
        this.amount = amount; 
    }
    
    public void setSource(String source) { 
        this.source = source; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
}